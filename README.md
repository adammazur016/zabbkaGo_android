# zabbkaGo_android
A school project - kotlin app with restful api

## Directories description
AcheivementRecyclerView - contains an adapter and the Class that is used in RecyclerView in Achievement Fragment
CommentRecyclerView - contains an adapter and the Class that is used to render comments
SubCommentRecyclerView - contains an adapter and the Class that is used to render subcomments
VisitedStoresRecyclerView - contains an adapter and the Class that is used when loading the visited places fragment
apiFunctions - contains all of the functions that directly make the API call
fragments - contains the fragments used in navigation
interfaces - the interfaces that tell how to make an API call, there is an endpoint and parameters declaration
responseDataClasses - for every endpoint they specify what should be received from the API

# All of the files are described in the code

# Configuration of RESTful API server IP address
The configuration file is RetrofitClient.
You can give your IP address if you pulled our flask docker file.