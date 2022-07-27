/// app.js
import React, { useState } from "react";
import { useSelector } from "react-redux";

import Map, { Layer, NavigationControl, Marker, Source } from "react-map-gl";
import RoomIcon from "@mui/icons-material/Room";
import { untransformCoordinates } from "../../app/utils/coordinatesTransformations";
import "mapbox-gl/dist/mapbox-gl.css";
import { useGetCuadrantsByScaleQuery } from "../../api/cuadrantApi";
import { selectToken } from "../user/login/LoginSlice";
import ControlPanel from "./ControlPanel";
import { Paper, Typography } from "@mui/material";

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
export default function CustomMap() {
  const token = useSelector(selectToken);

  const payload = {
    token: token,
    scale: "25.0",
  };

  const { data, error, isLoading } = useGetCuadrantsByScaleQuery(payload);

  const [cursor, setCursor] = useState("auto");
  const [mouseCoords, setMouseCoords] = useState({
    lng: 0,
    lat: 0,
  });

  const [settings, setSettings] = useState({
    minZoom: 7,
    maxZoom: 15,
  });

  return (
    <Map
      style={{ minWidth: "800px", minHeight: "600px" }}
      {...settings}
      initialViewState={INITIAL_VIEW_STATE}
      mapStyle={MAP_STYLE}
      mapboxAccessToken={MAPBOX_ACCESS_TOKEN2}
      onClick={(e) => setMouseCoords(e.lngLat)}
      cursor={cursor}
    >
      <div style={{ position: "absolute", zIndex: 1 }}>
        <NavigationControl />
      </div>

      {data &&
        data.map((item, index) => {
          const layerStyle = {
            id: item.id1.toString(),
            type: "fill",
            paint: {
              "fill-color": "blue",
              "fill-opacity": 0.3,
              "fill-outline-color": "red",
            },
          };

          const coord = item.coordinates.map((item, index) => {
            return [untransformCoordinates(item.x, item.y).longitude, untransformCoordinates(item.x, item.y).latitude];
          });

          const geoJson = {
            type: "FeatureCollection",
            features: [
              {
                type: "Feature",
                properties: {},
                geometry: {
                  type: "MultiPolygon",
                  coordinates: [[coord]],
                },
              },
            ],
          };
          return (
            <Source key={item.id1.toString()} type="geojson" data={geoJson}>
              <Layer {...layerStyle} />
            </Source>
          );
        })}

      <Marker latitude={mouseCoords.lat} longitude={mouseCoords.lng} anchor="bottom">
        <Paper sx={{ backgroundColor: "black", opacity: 0.6, padding: "5px" }}>
          <Typography variant="body" color="white" sx={{ display: "block" }}>
            Name:
          </Typography>
          <Typography variant="body" color="white" sx={{ display: "block" }}>
            Coords:
          </Typography>
          <Typography variant="body" color="white" sx={{ display: "block" }}>
            Temp stats:
          </Typography>
        </Paper>
      </Marker>
    </Map>
  );
}
