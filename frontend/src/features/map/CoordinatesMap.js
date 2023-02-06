/// app.js
import React, { useState } from "react";
import { useSelector } from "react-redux";

import Map, { Layer, NavigationControl, Marker, Source } from "react-map-gl";
import { transformCoordinates, untransformCoordinates } from "../../app/utils/coordinatesTransformations";
import "mapbox-gl/dist/mapbox-gl.css";
import { selectToken } from "../user/login/LoginSlice";
import Icon from "../../app/assets/images/pin.png";

const MAPBOX_ACCESS_TOKEN2 =
  "pk.eyJ1Ijoic2VhbmJvcmFtbGVlIiwiYSI6ImNrbTJlcnFqejE3NGQydXFtZng1cXR4eGgifQ.oZ0mZBtUX5u72QTPtPITfA";

const MAP_STYLE = "mapbox://styles/mapbox/outdoors-v11?optimize=true";

// Viewport settings
const INITIAL_VIEW_STATE = {
  longitude: -7.787,
  latitude: 43.0,
  zoom: 7,
  minZoon: 7,
  pitch: 0,
  bearing: 0,
};

// DeckGL react component
export default function CoordinatesMap({ childToParent }) {
  const token = useSelector(selectToken);

  const [cursor, setCursor] = useState("auto");

  const [mouseCoords, setMouseCoords] = useState({
    lng: 0,
    lat: 0,
  });

  const [settings, setSettings] = useState({
    minZoom: 7,
    maxZoom: 15,
  });
  const bounds = [
    [-10.353521, 40.958984], // northeastern corner of the bounds
    [-4.615985, 44.50585], // southwestern corner of the bounds
  ];

  const handleClick = (event) => {
    setMouseCoords(event.lngLat);

    childToParent([
      transformCoordinates(event.lngLat.lng, event.lngLat.lat).longitude,
      transformCoordinates(event.lngLat.lng, event.lngLat.lat).latitude,
    ]);
  };

  return (
    <Map
      style={{ minWidth: "800px", minHeight: "600px" }}
      {...settings}
      initialViewState={INITIAL_VIEW_STATE}
      mapStyle={MAP_STYLE}
      mapboxAccessToken={MAPBOX_ACCESS_TOKEN2}
      onClick={(e) => handleClick(e)}
      cursor={cursor}
      maxBounds={bounds}
    >
      <div style={{ position: "absolute", zIndex: 1 }}>
        <NavigationControl />
      </div>
      <Marker latitude={mouseCoords.lat} longitude={mouseCoords.lng} anchor="bottom">
        <img src={Icon} alt="map icon" width="50" />
      </Marker>
    </Map>
  );
}
