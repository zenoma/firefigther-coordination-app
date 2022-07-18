/// app.js
import React from "react";
import Map, { Layer, NavigationControl, Marker } from "react-map-gl";
import RoomIcon from "@mui/icons-material/Room";
import { Paper } from "@mui/material";
import "mapbox-gl/dist/mapbox-gl.css";

const MAPBOX_ACCESS_TOKEN2 =
  "pk.eyJ1Ijoic2VhbmJvcmFtbGVlIiwiYSI6ImNrbTJlcnFqejE3NGQydXFtZng1cXR4eGgifQ.oZ0mZBtUX5u72QTPtPITfA";

// Viewport settings
const INITIAL_VIEW_STATE = {
  longitude: -7.787,
  latitude: 43.0,
  zoom: 7,
  pitch: 0,
  bearing: 0,
};

// Data to be used by the LineLayer
const data = [{ sourcePosition: [-122.41669, 37.7853], targetPosition: [-122.41669, 37.781] }];

// DeckGL react component
export default function CustomMap() {
  const parkLayer = {
    id: "water",
    source: "mapbox-streets",
    "source-layer": "water",
    type: "fill",
    paint: {
      "fill-color": "#00ffff",
    },
  };

  return (
    <Map
      style={{ minWidth: "800px", minHeight: "600px" }}
      initialViewState={INITIAL_VIEW_STATE}
      mapStyle="mapbox://styles/mapbox/outdoors-v11"
      mapboxAccessToken={MAPBOX_ACCESS_TOKEN2}
    >
      <div style={{ position: "absolute", zIndex: 1 }}>
        <NavigationControl />
      </div>
      <Layer {...parkLayer} />
      <Marker longitude={-100} latitude={40} anchor="bottom">
        <RoomIcon color="error" />
      </Marker>
    </Map>
  );
}
