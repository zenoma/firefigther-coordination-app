/// app.js
import React, { useState } from "react";
import { useSelector } from "react-redux";

import { Paper, Typography } from "@mui/material";
import "mapbox-gl/dist/mapbox-gl.css";
import Map, { Layer, Marker, NavigationControl, Source } from "react-map-gl";
import { useGetQuadrantsByScaleQuery } from "../../api/quadrantApi";
import { untransformCoordinates } from "../../app/utils/coordinatesTransformations";
import { selectToken } from "../user/login/LoginSlice";

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
    scale: "50.0",
  };

  const { data } = useGetQuadrantsByScaleQuery(payload);

  const [cursor] = useState("auto");
  const [mouseCoords, setMouseCoords] = useState({
    lng: 0,
    lat: 0,
  });

  const [settings] = useState({
    minZoom: 7,
    maxZoom: 15,
  });
  const bounds = [
    [-10.353521, 40.958984], // northeastern corner of the bounds
    [-4.615985, 44.50585], // southwestern corner of the bounds
  ];

  return (
    <Map
      style={{ minWidth: "200px", minHeight: "500px" }}
      {...settings}
      initialViewState={INITIAL_VIEW_STATE}
      mapStyle={MAP_STYLE}
      mapboxAccessToken={MAPBOX_ACCESS_TOKEN2}
      //FIXME: Change on click to navigate to a new detailed view
      // onClick={(e) => setMouseCoords(e.lngLat)}
      // onClick={(e) => console.log(e.features)}
      cursor={cursor}
      maxBounds={bounds}
      //FIXME:To make layers interactive
      // interactiveLayerIds={["1364"]}
    >
      <div style={{ position: "absolute", zIndex: 1 }}>
        <NavigationControl />
      </div>

      {data &&
        data.map((item, index) => {
          const layerStyle = {
            id: item.id.toString(),
            type: "fill",
            paint: {
              "fill-color": "blue",
              "fill-opacity": 0.3,
              "fill-outline-color": "red",
            },
          };

          const coord = item.coordinates.map((item, index) => {
            return [
              untransformCoordinates(item.x, item.y).longitude,
              untransformCoordinates(item.x, item.y).latitude,
            ];
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
            <Source key={item.id.toString()} type="geojson" data={geoJson}>
              <Layer {...layerStyle} />
            </Source>
          );
        })}

      {/* FIXME: Move weather info to layer view */}
      {/* <Marker
        latitude={mouseCoords.lat}
        longitude={mouseCoords.lng}
        anchor="bottom"
        onClick={(e) => console.log(e)}
      >
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
      </Marker> */}
    </Map>
  );
}
