const location = {
  '1': { row: 2, col: 1},
  '2': { row: 2, col: 2},
  '3': { row: 2, col: 3},
  '4': { row: 3, col: 1},
  '5': { row: 3, col: 2},
  '6': { row: 3, col: 3},
  '7': { row: 5, col: 1},
  '8': { row: 5, col: 2},
  '9': { row: 5, col: 3},
  '10': { row: 6, col: 1},
  '11': { row: 6, col: 2},
  '12': { row: 6, col: 3},
  '13': { row: 6, col: 6},
  '14': { row: 6, col: 7},
  '15': { row: 6, col: 8},
  '16': { row: 7, col: 6},
  '17': { row: 7, col: 7},
  '18': { row: 7, col: 8}
}

export const getRowAndCol = (desk) => location[desk.deskNumber];