# AWS IP Addresses

The application allows to extract AWS IP addresses by regions.
The list of regions with IP property is received from:
https://ip-ranges.amazonaws.com/ip-ranges.json

### Tech
Java 11\
Sprint Boot 2.7.5

### Installation
The application is dockerized.
To run it using docker, the following commands should be fulfilled within local repository directory:\
`docker build -t ip-ranges-docker .`\
`docker run -p 9090:8080 ip-ranges-docker`

Use the link for the app when docker container is run:\
`http://localhost:9090/api/v1/ip-range`

### Tests
Junit Mockito and MockMvc

### How to use?
1. Run docker as per above steps.
2. Add the following variables to the URL instead of '\*'\
`http://localhost:9090/api/v1/ip-range?region=*`

'*' - (valid regions are EU, US, AP, CN, SA, AF, CA)

### License
MIT License