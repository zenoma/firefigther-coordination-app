/// app.js
import React from "react";
import Map, { Layer, NavigationControl, Marker, Source } from "react-map-gl";
import RoomIcon from "@mui/icons-material/Room";
import { untransformCoordinates } from "../../app/utils/coordinatesTransformations";
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

const coordinates1 = [
  [505092.55057272175, 4763130.201454596],
  [501697.5168363409, 4763128.516985962],
  [501696.9427907211, 4765442.035907892],
  [505090.82843518734, 4765443.720462895],
  [505092.55057272175, 4763130.201454596],
];

const coordinates2 = [
  [508487.5845002006, 4763133.570393844],
  [505092.55057272175, 4763130.201454596],
  [505090.82843518734, 4765443.720462895],
  [508484.7142687266, 4765447.08957488],
  [508487.5845002006, 4763133.570393844],
];

const coordinates3 = [
  [511886.6354948951, 4760825.112815411],
  [508490.4536030551, 4760820.059663359],
  [505094.2720330837, 4760816.69089863],
  [501698.0906562364, 4760815.006517257],
  [498301.90934376407, 4760815.006517257],
  [494905.7279669167, 4760816.69089863],
  [491509.5463969453, 4760820.059663359],
  [488113.3645051053, 4760825.112815411],
  [484717.18216265866, 4760831.850360737],
  [484722.3465619797, 4763145.3617020175],
  [484727.5129928073, 4765458.881487617],
  [484732.68145447416, 4767772.409718013],
  [484737.8519463127, 4770085.946393672],
  [484743.024467655, 4772399.491515063],
  [484748.1990178329, 4774713.045082643],
  [484753.37559617806, 4777026.607096869],
  [484758.5542020216, 4779340.177558194],
  [488145.5427076205, 4779333.437320435],
  [491532.5308003548, 4779328.382149007],
  [494919.5185981789, 4779325.012038003],
  [498306.5062190543, 4779323.326983484],
  [501693.49378094624, 4779323.326983484],
  [505080.4814018215, 4779325.012038003],
  [508467.4691996456, 4779328.382149007],
  [511854.4572923799, 4779333.437320435],
  [511858.4850906478, 4777019.8671831945],
  [511862.5113119687, 4774706.305496616],
  [511866.5359558224, 4772392.752260246],
  [511870.55902168906, 4770079.207473629],
  [511874.580509049, 4767765.671136306],
  [511878.6004173828, 4765452.143247808],
  [511882.6187461711, 4763138.623807669],
  [511886.6354948951, 4760825.112815411],
];
const cuadrants = [
  { name: "1", coordinates: coordinates1 },
  { name: "2", coordinates: coordinates2 },
  { name: "3", coordinates: coordinates3 },
];

// DeckGL react component
export default function CustomMap() {
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

      {cuadrants.map((item, index) => {
        const layerStyle = {
          id: item.name,
          type: "fill",
          paint: {
            "fill-color": "green",
            "fill-opacity": 0.5,
            "fill-outline-color": "red",
          },
        };

        const coord = item.coordinates.map((item, index) => {
          return [
            untransformCoordinates(item[0], item[1]).longitude,
            untransformCoordinates(item[0], item[1]).latitude,
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
          <Source key={index} type="geojson" data={geoJson}>
            <Layer {...layerStyle} />
          </Source>
        );
      })}

      <Marker longitude={-100} latitude={40} anchor="bottom">
        <RoomIcon color="error" />
      </Marker>
    </Map>
  );
}
