import PropTypes from "prop-types";
import React, { useState, useRef } from "react";

import "mapbox-gl/dist/mapbox-gl.css";
import Map, { Layer, NavigationControl, Source } from "react-map-gl";
import { untransformCoordinates } from "../../app/utils/coordinatesTransformations";

const MAPBOX_ACCESS_TOKEN =
  "pk.eyJ1IjoiemVub21hIiwiYSI6ImNrdnM2eWdjNDRrZHcyb3E1NzBtbnlpaHYifQ.9lnCH-vo6CB38AsRXw_aZQ";

const MAP_STYLE = "mapbox://styles/zenoma/ckvs7r0750sfc14l559g5i461/draft";

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
export default function CustomMap(props) {
  const quadrants = props.quadrants;
  const mapRef = useRef(null); // Crear la referencia usando useRef

  const [cursor] = useState("auto");
  // const [mouseCoords, setMouseCoords] = useState({
  //   lng: 0,
  //   lat: 0,
  // });

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
      ref={mapRef}
      style={{ minWidth: "200px", minHeight: "500px" }}
      {...settings}
      initialViewState={INITIAL_VIEW_STATE}
      mapStyle={MAP_STYLE}
      mapboxAccessToken={MAPBOX_ACCESS_TOKEN}
      //FIXME: Change on click to navigate to a new detailed view
      // onClick={(e) => setMouseCoords(e.lngLat)}
      onClick={(e) => console.log(e.features)}
      cursor={cursor}
      maxBounds={bounds}
      //FIXME:To make layers interactive
      // interactiveLayerIds={["1364"]}
    >
      <div style={{ position: "absolute", zIndex: 1 }}>
        <NavigationControl />
      </div>

      {quadrants &&
        quadrants.map((item, index) => {
          const coord = item.coordinates.map((item, index) => {
            return [
              untransformCoordinates(item.x, item.y).longitude,
              untransformCoordinates(item.x, item.y).latitude,
            ];
          });

          const teamSize = item.teamDtoList ? item.teamDtoList.length : -1;
          const vehicleSize = item.vehicleDtoList
            ? item.vehicleDtoList.length
            : -1;

          const quadrantLabelStyle = {
            id: item.id.toString() + "-label",
            minzoom: 11,
            type: "symbol",
            source: "label",
            layout: {
              "text-field": "{place-name} #{place-id} ",
              "text-size": 15,
              "text-anchor": "center",
              "text-offset": [0, -2],
            },
            paint: {
              "text-color": "black",
              "text-halo-color": "#fff",
              "text-halo-width": 1,
            },
          };

          const vehiclesLabelStyle = {
            id: item.id.toString() + "-vehicle-label",
            minzoom: 11,
            type: "symbol",
            source: "label",
            layout: {
              "text-field": "{vehicle-size}",
              "text-size": 15,
              "text-anchor": "center",
              "text-offset": [-1.5, 1.8],
              "icon-image": "fire-truck",
              "icon-size": 0.4,
              "icon-anchor": "center",
              "icon-offset": [0, 70],
            },
            paint: {
              "text-color": "white",
              "text-halo-color": "green",
              "text-halo-width": 1,
            },
          };

          const teamLabelStyle = {
            id: item.id.toString() + "-team-label",
            minzoom: 11,
            type: "symbol",
            source: "label",
            layout: {
              "text-field": "{team-size}",
              "text-size": 15,
              "text-anchor": "center",
              "text-offset": [-1.3, 0],
              "icon-image": "team",
              "icon-size": 0.2,
              "icon-anchor": "center",
              "icon-offset": [0, 0],
            },
            paint: {
              "text-color": "white",
              "text-halo-color": "green",
              "text-halo-width": 1,
            },
          };

          const quadrantLayerStyle = {
            id: item.id.toString() + "-layer",
            type: "fill",
            layout: {},
            paint: {
              "fill-color": "red",
              "fill-opacity": 0.4,
            },
          };

          const quadrantBorderStyle = {
            id: item.id.toString() + "-border",
            type: "line",
            layout: {},
            paint: {
              "line-color": "#000",
              "line-width": 1,
            },
          };

          const geoJson = {
            type: "FeatureCollection",
            features: [
              {
                type: "Feature",
                properties: Object.assign({}, item, {
                  "place-name": item.nombre,
                  "place-id": item.id,
                  "team-size": teamSize,
                  "vehicle-size": vehicleSize,
                }),
                geometry: {
                  type: "MultiPolygon",
                  coordinates: [[coord]],
                },
              },
            ],
          };
          return (
            <Source key={item.id.toString()} type="geojson" data={geoJson}>
              <Layer {...quadrantBorderStyle} />
              <Layer {...quadrantLayerStyle} />
              {teamSize === -1 ? undefined : <Layer {...teamLabelStyle} />}
              {vehicleSize === -1 ? undefined : (
                <Layer {...vehiclesLabelStyle} />
              )}
              <Layer {...quadrantLabelStyle} />
            </Source>
          );
        })}
    </Map>
  );
}

CustomMap.propTypes = {
  quadrants: PropTypes.array.isRequired,
};

{
  /* FIXME: Move weather info to layer view */
}
{
  /* <Marker
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
      </Marker> */
}
