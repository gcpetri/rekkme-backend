# Rekkme-Backend

## Build and Run ##

Windows
```
.\ps-init.ps1
```

Linux
```
./unix-init.sh
```

## Hit Endpoints Locally ##
http://localhost:8080/rekkme/api

## Connect the database (Docker Container: bin/db) ##
```
psql -h localhost -p 5432 -U compose-postgres -W
```