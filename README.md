Create an event  

curl --location 'http://localhost:8080/api/events' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID.346ef129=node0o6933i320x5k1qz5njrvg3iz2.node0' \
--data '{
    
    "name": "AWS certified cloud practitioner",
    "description": "Become AWS certified",
    "totalSeats": 10,
    "bookedSeats": 0,
    "bookings": null
}'


Create a booking 

curl --location --request POST 'http://localhost:8080/api/events/1/book?seatNumber=1c' \
--header 'x-username: Attendee 1' \
--header 'Cookie: JSESSIONID.346ef129=node0o6933i320x5k1qz5njrvg3iz2.node0' \
--data ''

Increments the event booked number by 1 for each booking confirming "booked" to user until it is fully booked. When fully booked it adds person to waiting list and stores timestamp to allow for the first person added to the waiting list to be prioritised if there is a cancellation.

Console messages are used in place of actions which should be performed such as sending confirmation emails etc.

Cancellation of a booking 

curl --location --request POST 'http://localhost:8080/api/events/1/cancel?bookingId=1' \
--header 'Cookie: JSESSIONID.346ef129=node0o6933i320x5k1qz5njrvg3iz2.node0' \
--data ''

Get Bookings for an event

curl --location 'http://localhost:8080/api/events/1/bookings' \
--header 'Cookie: JSESSIONID.346ef129=node0o6933i320x5k1qz5njrvg3iz2.node0' \
--data ''

Get number of seats available for an event

curl --location 'http://localhost:8080/api/events/2/availableSeats' \
--header 'Cookie: JSESSIONID.346ef129=node0o6933i320x5k1qz5njrvg3iz2.node0'

List people on waiting List

curl --location 'http://localhost:8080/api/events/1/waitingList' \
--header 'Cookie: JSESSIONID.346ef129=node0o6933i320x5k1qz5njrvg3iz2.node0'
