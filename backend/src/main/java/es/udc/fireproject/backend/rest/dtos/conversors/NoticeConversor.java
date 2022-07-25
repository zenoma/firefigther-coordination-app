package es.udc.fireproject.backend.rest.dtos.conversors;

import es.udc.fireproject.backend.model.entities.notice.Notice;
import es.udc.fireproject.backend.rest.dtos.NoticeDto;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;

public class NoticeConversor {

    private NoticeConversor() {
    }

    public static Notice toNotice(NoticeDto noticeDto) {
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 25829);
        Coordinate coordinate = new Coordinate(noticeDto.getLon(), noticeDto.getLat());

        return new Notice(noticeDto.getBody(),
                noticeDto.getStatus(),
                geometryFactory.createPoint(coordinate));
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
