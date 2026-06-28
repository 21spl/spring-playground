# 22 - Implementing Domain Models

## Objective
Translate a Domain Model into Java classes with correct JPA annotations and relationships


## Task 1 - The Address Value Object

- Address is shared by three associations on User(home, billing, shipping)
- It has no identity of its own - so not an entity
- write it as @Embeddable class, not an @Entity class

```java
@Embeddable
public class Address{

    private String street;
    private String zipcode;
    private String city;

    // jpa requires no-arg constructor
    protected Address(){}

    public String getStreet() {
        return street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "Address [street=" + street + ", zipcode=" + zipcode + ", city=" + city + "]";
    }

    // Address is a value object
    // value objects should be immutable
    // so no setters - only getters
}
```


## Task 2 - Implementing the BillingModeHierarchy

- Write the BillingDetails abstract class and its two concrete subclasses ```CreditCard``` and ```UPI```
- Use @MappedSuperclass annotation - Hibernate reads its mappings but don't create a table for it. subclasses get their tables with inherited fields included

```java
@MappedSuperclass
public abstract class BillingMode {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    // common to all billing strategies
    @Column(nullable=false)
    private String owner; // name on the bank account/cc


    // no arg constructor - must for jpa
    public BillingMode(){}

    public BillingMode(String owner){
        this.owner = owner;
    }

    // getters - id, owner

    public Long getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }
}
```

```java
@Entity
@Table(name= "CREDIT_CARD")
public class CreditCard  extends BillingMode{
    
    @Column(nullable = false)
    private String ccNumber;

    private int expMonth;
    private int expYear;

    // no arg constructor
    public CreditCard(){}

    public CreditCard(String owner, String ccNumber, int expMonth, int expYear) {
        super(owner);
        this.ccNumber = ccNumber;
        this.expMonth = expMonth;
        this.expYear = expYear;
    }

    public String getCcNumber() {
        return ccNumber;
    }


    public int getExpMonth() {
        return expMonth;
    }


    public int getExpYear() {
        return expYear;
    }  
}
```

```Java
@Entity
@Table(name="UPI")
public class UPI extends BillingMode {
    
    private String upiId;

    // no arg constructor
    public UPI(){}

    // all arg constructor
    public UPI(String owner, String upiId){
        super(owner);
        this.upiId = upiId;
    }

    public String getUpiId() {
        return upiId;
    }
}
```

## Task 3 - User Entity

```java
@Entity
@Table(name="USERS")
public class User {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String username;

    private String firstname;
    private String lastname;

    // Three Address Embeddings in one USERS table row

    // @AttributeOverride renames the columns to avoid name collision

    // homeAddress column
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="street", column= @Column(name="HOME_STREET")),
        @AttributeOverride(name="zipcode", column= @Column(name="HOME_ZIPCODE")),
        @AttributeOverride(name="city", column= @Column(name="HOME_CITY"))
    })
    private Address homeAddress;

    // billingAddress column
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="street", column = @Column(name="BILLING_STREET")),
        @AttributeOverride(name="zipcode", column = @Column(name="BILLING_ZIPCODE")),
        @AttributeOverride(name="city", column = @Column(name="BILLING_CITY"))
    })
    private Address billingDetails;

    // shippingAddress column
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="street", column = @Column(name="SHIPPING_STREET")),
        @AttributeOverride(name="zipcode", column = @Column(name="SHIPPING_ZIPCODE")),
        @AttributeOverride(name="city", column = @Column(name="SHIPPING_CITY"))
    })
    private Address shippingDetails;

    // no-arg constructor
    public User(){}

    // args constructor
    public User(String username, String firstname, String lastname){
        this.username=username;
        this.firstname=firstname;
        this.lastname=lastname;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public Address getBillingDetails() {
        return billingDetails;
    }

    public Address getShippingDetails() {
        return shippingDetails;
    }

    // setters for addresses only
    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public void setBillingDetails(Address billingDetails) {
        this.billingDetails = billingDetails;
    }

    public void setShippingDetails(Address shippingDetails) {
        this.shippingDetails = shippingDetails;
    }
}
```

## Task 4 - Category Entity

```java
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    // self- referential many to one: many categories share one parent
    @ManyToOne
    @JoinColumn(name="PARENT_ID") // FK column in the CATEGORY table
    private Category parent; // null for root categories

    // inverse side of the parent relationship - one category can have multiple children
    @OneToMany(mappedBy="parent")
    private Set<Category> children;

    // no-arg constructor
    public Category(){}

    // arg constructor 1
    public Category(String name){
        this.name = name;
    }

    // arg constructor 2
    public Category(String name, Category parent){
        this.name = name;
        this.parent = parent;
        parent.getChildren().add(this); // keep both sides in sync
    }

    // helper: add a child Category
    // for Collections type we don't write setChildren
    public void addChild(Category child){
        child.parent = this;
        this.children.add(child);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getParent() {
        return parent;
    }

    public Set<Category> getChildren() {
        return children;
    }

    // method to check root category
    public boolean isRoot(){
        return parent==null;
    }

    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + "]";
    }
}
```

## Task 5 - Create the persistence layer - CategoryRepository and UserRepository

```java
public interface CategoryRepository extends JpaRepository<Category, Long>{
    
    List<Category> findByParentIsNull(); 

    List<Category> findByName(String name);

    List<Category> findByParent(Category parent);
}


/*
we do not add @Repository annotation on interfaces that extend JpaRepository in spring data jpa

Spring data jpa automatically detects any interface extends Repository or JpaRepository because of automatic
component scanning

we only need to ensure that the main application class (or configuration class is annotated with 
@EnableJpaRepositories
*/
```

```java
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
```

## Task 6 - Create the CategoryService that contains business rules

```java
@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;

    // constructor injection
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Category createRootCategory(String name){
        Category category = new Category(name);
        return categoryRepository.save(category);
    }
    
    @Transactional
    public Category createChildCategory(String name, Long parentId){

        // step 1: Find the parent by Id - if not exists, throw an exception
        Category parent = categoryRepository.findById(parentId)
            .orElseThrow(()-> new IllegalArgumentException(
                "Parent category not found: " + parentId));

        // step 2: Now create the child category
        Category category = new Category(name, parent);
        // save the child category
        return categoryRepository.save(category);
    }

    // Business rule: a category can only be deleted if its has no child
    @Transactional
    public void deleteCategory(Long categoryId){
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryId));
        
        if(!category.getChildren().isEmpty()){
            throw new IllegalStateException(
                "Cannot delete category" + category.getName() + 
                " - it has " + category.getChildren().size() + " child categories");   
        }

        categoryRepository.delete(category);
    }

    // why aren't we using @Transactional annotation here
    // use @Transaction for making any insert/update/delete - basically modifying the db state,
    // in the following method
    public List<Category> getRootCategories(){
        return categoryRepository.findByParentIsNull(); 
    }
}
```

## Task 7 - Verify Layer Boundaries with a Test

- Write a test that uses `CategoryService`(not `CategoryRepository` directly).
- verify that the business rule "cannot delete a category with children" is enforced

```java

@SpringBootTest
@Transactional // rollback after each test - keeps DB clean
public class CategoryServiceTest {
    
    @Autowired
    private CategoryService categoryService;

    @Test
    void rootCategoryCreation(){
        Category electronics = categoryService.createRootCategory("Electronics");

        assertNotNull(electronics.getId());
        assertEquals("Electronics", electronics.getName());
        assertTrue(electronics.isRoot());
    }

    @Test
    void createCategoryHierarchy(){
        Category electronics = categoryService.createRootCategory("Electronics");
        Category phones = categoryService.createChildCategory("Phones", electronics.getId());
        Category laptops = categoryService.createChildCategory("Laptops", electronics.getId());

        assertEquals("Electronics", phones.getParent().getName());
        assertEquals("Electronics", laptops.getParent().getName());
    }

    @Test
    void cannotDeleteCategoryWithChildren(){
        
        Category electronics = categoryService.createRootCategory("Electronics");
        categoryService.createChildCategory("Phones", electronics.getId());

        // Business rule: show throw Electronics has children
        IllegalStateException ex = assertThrows(
            IllegalStateException.class, 
            () -> categoryService.deleteCategory(electronics.getId())
        );

        assertTrue(ex.getMessage().contains("child categories"));
    }

    @Test
    void canDeleteLeafCategory(){
        Category electronics = categoryService.createRootCategory("Electronics");
        Category phones = categoryService.createChildCategory("Phones", electronics.getId());

        // Phones is a leaf - no children - should succeed
        assertDoesNotThrow(() -> categoryService.deleteCategory(phones.getId()));
    }
}
```





