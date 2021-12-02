package es.udc.fireproject.backend.utils;

import es.udc.fireproject.backend.model.entities.organization.Organization;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrganizationOM {
    public static Organization withDefaultValues() {
        final GeometryFactory geoFactory = new GeometryFactory();

        return new Organization("ORG-01",
                "Centro de Coordinación Central",
                "Calle alguna", geoFactory.createPoint(new Coordinate(-45, 45)),
                OrganizationTypeOM.withDefaultValues());


    }

    public static Organization withOrganizationTypeAndRandomNames(String name) {
        final GeometryFactory geoFactory = new GeometryFactory();
        byte[] array = new byte[7]; // length is bounded by 7
        new Random().nextBytes(array);
        String generatedString = new String(array, StandardCharsets.UTF_8);
        return new Organization(generatedString.substring(0, 4),
                generatedString,
                "Calle alguna",
                geoFactory.createPoint(new Coordinate(-45, 45)),
                OrganizationTypeOM.withNames(List.of(name)).stream().findFirst().orElse(OrganizationTypeOM.withDefaultValues()));

    }

    public static Organization withInvalidValues() {
        return new Organization("",
                "",
                "", null,
                OrganizationTypeOM.withInvalidName());
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