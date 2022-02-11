package es.udc.fireproject.backend.rest.dtos.conversors;

import es.udc.fireproject.backend.model.entities.organization.Organization;
import es.udc.fireproject.backend.model.entities.organization.OrganizationType;
import es.udc.fireproject.backend.model.services.organization.OrganizationService;
import es.udc.fireproject.backend.rest.dtos.OrganizationDto;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;

public class OrganizationConversor {

    public static final int SRID = 25829;
    @Autowired
    static
    OrganizationService organizationService;

    private OrganizationConversor() {

    }

    public static Organization toOrganization(OrganizationDto organizationDto, OrganizationType organizationType) {

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), SRID);
        Coordinate coordinate = new Coordinate(organizationDto.getLon(), organizationDto.getLat());
        Point point = geometryFactory.createPoint(coordinate);
        point.setSRID(SRID);

        return new Organization(organizationDto.getCode(),
                organizationDto.getName(),
                organizationDto.getHeadquartersAddress(),
                point,
                organizationType);
    }

    public static Organization toOrganization(OrganizationDto organizationDto) {

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), SRID);
        Coordinate coordinate = new Coordinate(organizationDto.getLon(), organizationDto.getLat());
        Point point = geometryFactory.createPoint(coordinate);
        point.setSRID(SRID);


        return new Organization(organizationDto.getCode(),
                organizationDto.getName(),
                organizationDto.getHeadquartersAddress(),
                point,
                new OrganizationType());
    }


    public static OrganizationDto toOrganizationDto(Organization organization) {
        return new OrganizationDto(organization.getId(),
                organization.getCode(),
                organization.getName(),
                organization.getHeadquartersAddress(),
                organization.getLocation().getX(),
                organization.getLocation().getY(),
                organization.getCreatedAt(),
                organization.getOrganizationType().getName());

    }
}
