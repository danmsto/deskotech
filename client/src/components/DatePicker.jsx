import { useContext, useEffect } from "react";

import AppContext from "../context";

const DatePicker = () => {

  const { client, selectedDate, setSelectedDate } = useContext(AppContext);

  useEffect(() => {
    client.getDesksWithBookings(selectedDate);
  }, [selectedDate])

  const handleChange = (event) => {
    const date = new Date(event.target.value);
    setSelectedDate(date)
  }

  return (
    <>
      <div className="datepicker">
        <label htmlFor="datePickerId" >Showing Desks for Date</label>
        <input type="date" id="datePickerId" className="form-control" onChange={handleChange} 
            value={`${selectedDate.getFullYear()}-${selectedDate.getMonth() + 1}-${selectedDate.getDate()}`}/>
      </div>
    </>
  )
}

export default DatePicker;
