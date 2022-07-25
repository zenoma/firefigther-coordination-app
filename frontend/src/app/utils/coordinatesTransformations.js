import proj4 from "proj4";

export const transformCoordinates = (position) => {
  var firstProjection = proj4("EPSG:4326");
  var secondProjection = "+proj=utm +zone=29 +ellps=GRS80 +towgs84=0,0,0,0,0,0,0 +units=m +no_defs";

  var result = proj4(firstProjection, secondProjection).forward([position.coords.longitude, position.coords.latitude]);
  return { longitude: result[0], latitude: result[1] };
};
