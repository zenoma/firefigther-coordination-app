package es.udc.fireproject.backend.utils;

import es.udc.fireproject.backend.model.entities.organization.OrganizationType;

public class OrganizationTypeOM {
    public static OrganizationType withDefaultValues() {
        return new OrganizationType("Dummy Type Name");
    }

    public static OrganizationType withInvalidName() {
        OrganizationType organizationType = withDefaultValues();
        organizationType.setName(null);
        return organizationType;
    }
}
