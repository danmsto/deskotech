import { useContext } from "react";

import { checkUserHasBooking } from "./deskUtils";

import AppContext from "../context";
import Desk from "./Desk";

import Row from "react-bootstrap/Row";
import Col from "react-bootstrap/Col";

import { getRowAndCol } from "./deskLocations";
import { getRotation } from "./deskRotations.js"

const DeskViewer = () => {
  const { user, desks } = useContext(AppContext);
  const userHasBookingToday = checkUserHasBooking(user, desks);

  const cols = [1, 2, 3, 4, 5, 6, 7, 8];
  const rows = [1, 2, 3, 4, 5, 6, 7, 8];

  const getDeskForRowAndCol = (row, col) => {
    const desk = desks.find((desk) => {
      const location = getRowAndCol(desk)
      return location.row === row && location.col === col
    })
    if (!desk) {
      return null
    }
    const rotation = getRotation(desk);
    return (
        <Desk key={row + col} desk={desk} rotation={rotation} userHasBookingToday={userHasBookingToday}/>
    )
  }

  return (
      <div className="officeContainer">
        <div className="mt-2 p-0 officeGrid">
          {rows.map((row) => (
              <Row
                  key={row}
                  id={row}
                  className="officeRow p-0"
                  style={{ marginLeft: "0"}}
              >
                {cols.map((col) =>
                    <Col
                      key={col} className="officeColumn p-0" id={col}
                    >
                      {getDeskForRowAndCol(row, col)}
                    </Col>
                )}
              </Row>
          ))}
        </div>
      </div>

  )
}

export default DeskViewer;