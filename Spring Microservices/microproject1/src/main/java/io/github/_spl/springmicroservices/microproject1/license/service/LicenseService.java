package io.github._spl.springmicroservices.microproject1.license.service;

import io.github._spl.springmicroservices.microproject1.license.model.License;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LicenseService {

    // method to getLicense
    public License getLicense(String licenseId, String organizationId)
    {
        License license = new License();

        license.setId(new Random().nextInt(1000));
        license.setLicenseId(licenseId);
        license.setOrganizationId(organizationId);
        license.setDescription("software product");
        license.setLicenseType("full");

        return license;
    }

    // method to createLicense
    public String createLicense(License license, String organizationId)
    {
        String responseMessage = null;
        if(license!=null) {
            license.setOrganizationId(organizationId);
            responseMessage = String.format("This is a POST method and the object is: %s", license.toString());
        }
        return responseMessage;
    }

    // method to updateLicense
    public String updateLicense(License license, String organizationId)
    {
        String responseMessage = null;
        if(license!=null)
        {
            license.setOrganizationId(organizationId);
            responseMessage = String.format("This is a PUT method and the object is: %s", license.toString());
        }
        return responseMessage;
    }

    public String deleteLicense(String licenseId, String organizationId){
        String responseMessage = null;
        responseMessage = String.format("Deleting license with id %s for the organization %s",licenseId, organizationId);
        return responseMessage;
    }
}
