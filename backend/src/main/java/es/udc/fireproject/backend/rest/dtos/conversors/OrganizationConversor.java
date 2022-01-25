package es.udc.fireproject.backend.rest.dtos.conversors;

import es.udc.fireproject.backend.model.entities.organization.Organization;
import es.udc.fireproject.backend.model.entities.organization.OrganizationType;
import es.udc.fireproject.backend.rest.dtos.OrganizationDto;
import es.udc.fireproject.backend.rest.dtos.OrganizationDtoBuilder;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;

public class OrganizationConversor {

    private OrganizationConversor() {

    }

    public static Organization toOrganization(OrganizationDto organizationDto, OrganizationType organizationType) {

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 25829);
        Coordinate coordinate = new Coordinate(organizationDto.getLat(), organizationDto.getLon());


        return new Organization(organizationDto.getCode(),
                organizationDto.getName(),
                organizationDto.getHeadquartersAddress(),
                geometryFactory.createPoint(coordinate),
                organizationType);
    }

    public static OrganizationDto toOrganizationDto(Organization organization) {
        return new OrganizationDtoBuilder().setId(organization.getId()).setCode(organization.getCode()).setName(organization.getName()).setHeadquartersAddress(organization.getHeadquartersAddress()).setLon(organization.getLocation().getX()).setLat(organization.getLocation().getY()).setCreatedAt(organization.getCreatedAt()).setOrganizationTypeName(organization.getOrganizationType().getName()).createOrganizationDto();
    }
}
