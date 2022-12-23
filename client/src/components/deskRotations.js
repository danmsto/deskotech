const rotation = {
  '1': { deg: 0 },
  '2': { deg: 0 },
  '3': { deg: 0 },
  '4': { deg: 180 },
  '5': { deg: 180 },
  '6': { deg: 180 },
  '7': { deg: 0 },
  '8': { deg: 0 },
  '9': { deg: 0 },
  '10': { deg: 180 },
  '11': { deg: 180 },
  '12': { deg: 180 },
  '13': { deg: 0 },
  '14': { deg: 0 },
  '15': { deg: 0 },
  '16': { deg: 180 },
  '17': { deg: 180 },
  '18': { deg: 180 }
}

export const getRotation = (desk) => rotation[desk.deskNumber];