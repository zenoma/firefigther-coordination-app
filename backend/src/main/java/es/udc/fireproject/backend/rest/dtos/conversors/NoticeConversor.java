package es.udc.fireproject.backend.rest.dtos.conversors;

import es.udc.fireproject.backend.model.entities.notice.Notice;
import es.udc.fireproject.backend.rest.dtos.NoticeDto;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public class NoticeConversor {
    public static final int SRID = 25829;

    private NoticeConversor() {
    }

    public static Notice toNotice(NoticeDto noticeDto) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), SRID);
        Coordinate coordinate = new Coordinate(noticeDto.getLon(), noticeDto.getLat());
        Point point = geometryFactory.createPoint(coordinate);
        point.setSRID(SRID);

        return new Notice(noticeDto.getBody(),
                noticeDto.getStatus(),
                point);
    }


    public static NoticeDto toNoticeDto(Notice notice) {
        if (notice.getUser() != null) {
            return new NoticeDto(notice.getId(),
                    notice.getBody(),
                    notice.getStatus(),
                    notice.getCreatedAt(),
                    UserConversor.toUserDto(notice.getUser()),
                    notice.getLocation().getX(),
                    notice.getLocation().getY());
        } else {
            return new NoticeDto(notice.getId(),
                    notice.getBody(),
                    notice.getStatus(),
                    notice.getCreatedAt(),
                    notice.getLocation().getX(),
                    notice.getLocation().getY());
        }

    }
}
