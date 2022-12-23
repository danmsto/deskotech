export const getDateObject = (dateValue) => {
  const date = new Date(dateValue);
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  return { year, month, day };
};

export const isBeforeToday = (date, today) => {
  today.setHours(0, 0, 0, 0);
  return date < today;
}

export const getToday = () => new Date();