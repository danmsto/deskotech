import { checkUserHasBooking } from './deskUtils.js'

describe ('checkUserHasBooking', () => {
    const user = {
        "username": "Chris",
        "id": 2
    };
    const desksNoBookings = [
        {
            "id": 1,
            "deskNumber": 1,
            "booking": null
        },
        {
            "id": 2,
            "deskNumber": 2,
            "booking": null
        },
        {
            "id": 3,
            "deskNumber": 3,
            "booking": null
        }
    ]
    const desksWithBookingDifferentUser = [
        {
            "id": 1,
            "deskNumber": 1,
            "booking": null
        },
        {
            "id": 2,
            "deskNumber": 2,
            "booking": null
        },
        {
            "id": 3,
            "deskNumber": 3,
            "booking": {
                "id": 5,
                "date": [
                    2022,
                    12,
                    15
                ],
                "deskId": 2,
                "user": {
                    "username": "James",
                    "id": 3
                }
            }
        }
    ]

    const desksWithBookingSameUser = [
        {
            "id": 1,
            "deskNumber": 1,
            "booking": null
        },
        {
            "id": 2,
            "deskNumber": 2,
            "booking": null
        },
        {
            "id": 3,
            "deskNumber": 3,
            "booking": {
                "id": 5,
                "date": [
                    2022,
                    12,
                    15
                ],
                "deskId": 2,
                "user": {
                    "username": "Chris",
                    "id": 2
                }
            }
        }
    ]

    const desksNoDesks = []; 
 
    test('no bookings for the user, returns false', () => {
        expect(checkUserHasBooking(user, desksWithBookingDifferentUser)).toBe(false)
    })
    test('no bookings for any user, returns false', () => {
        expect(checkUserHasBooking(user, desksNoBookings)).toBe(false)
    })
    test('has booking for the user, return true', () => {
        expect(checkUserHasBooking(user, desksWithBookingSameUser)).toBe(true)
    })
    test('empty desks, return false', () => {
        expect(checkUserHasBooking(user, desksNoDesks)).toBe(false)
    })
});
