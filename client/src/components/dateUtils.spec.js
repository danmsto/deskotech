import { isBeforeToday } from "./dateUtils";

describe("isBeforeToday", () => {
  const mockedToday = new Date(2022, 11, 25);
  const pastDate = new Date(2022, 11, 24);
  const futureDate = new Date(2022, 11, 26);

  test("past date is before today", () => {
    expect(isBeforeToday(pastDate, mockedToday)).toBe(true);
  });

  test("future date is not before today", () => {
    expect(isBeforeToday(futureDate, mockedToday)).toBe(false);
  });
});
