package es.udc.fireproject.backend.utils;

import es.udc.fireproject.backend.model.entities.organization.Organization;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;

import java.util.ArrayList;
import java.util.List;

public class OrganizationOM {
    public static Organization withDefaultValues() {
        final GeometryFactory geoFactory = new GeometryFactory();

        return new Organization("ORG-01",
                "Centro de Coordinación Central",
                "Calle alguna", geoFactory.createPoint(new Coordinate(-45, 45)),
                OrganizationTypeOM.withDefaultValues());
    }

    public static List<Organization> withNames(List<String> names) {
        final GeometryFactory geoFactory = new GeometryFactory();
        final List<Organization> result = new ArrayList<>();
        Organization organization;
        int count = 0;

        for (String name : names) {
            organization = new Organization(name.substring(0, 3).toUpperCase() + "-" + count,
                    name,
                    "Calle alguna", geoFactory.createPoint(new Coordinate(-45, 45)),
                    OrganizationTypeOM.withDefaultValues());
            result.add(organization);
            count++;
        }
        return result;

    }
}