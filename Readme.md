# Library service
A simple library app designed to keep track of a user's book library

## Running the app

### Prerequisites
- Docker installed
- Docker engine running

To run the image ensure you are in the root directory and run 

```bash
docker compose -f docker/compose.yaml up  
```
This will start the app on port 8080

To stop the app stay in the root directory and run
```bash
docker compose -f docker/compose.yaml down  
```