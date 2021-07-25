To run project simply fetch it and run gradle sync

Possible improvements
1. Add credentials.properties file and move all hardcoded creds there. It mustn't be on remote for security reasons. Usually I download it from CI secure file storage before running.
2. Replace realm async calls with sync ones wrapped with some "observable"-like or coroutines  to have better control over their execution
3. Check accuracy of ArcGIS data, because now it surprisingly returns coordinates of places that are far away from our current position