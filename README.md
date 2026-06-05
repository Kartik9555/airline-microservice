# airline-microservice
1. Create City
2. Create Airport for the City
3. Create Airline for the Airport
4. Create Aircraft for the Airline
5. Create Ancillary for the Airline
6. Create Meal for the Airline
7. Create Insurance Coverage for the Ancillary 
8. Create Cabin Class for the Aircraft 
9. Create Seat Map for the Cabin Class (Will create Seats for the Seat Map)
10. Create Flight for the Airline and Aircraft
11. Create FlightMeal for the Flight
12. Create FlightCabinAncillary for the Flight and Cabin Class 
13. Create Flight Schedule for the Flight (Will create Flight Instances for the Flight Schedule, 
send "flight_instance_created" and creates Flight Instance Cabin and Seat Instance for the Flight Instance Cabin)
14. Search for Flights based on origin, destination and date 
15. Book a Flight Instance for a Passenger (Will create Booking and Booking Instance for the Flight Instance, send "booking_created" and creates Booking Instance Cabin and Seat for the Booking Instance Cabin)