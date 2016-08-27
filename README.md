# Authorization Manager

The SocIoTal Authorization scenario consists of the following main entities:

* **Capability Client**. It is a HTTPS client, which is intended to making request to the Capability Manager to obtain capability tokens, which are used to get access to resources hosted by other devices.
* **Capability Evaluator**. It is a library intended to evaluate capability tokens. Such evaluation is based on the action and device being requested and the use of KeyRock authentication credentials. 
* **Capability Manager**. It is a HTTPS server accepting requests for capability tokens generation. Additionally, this entity acts as a HTTP client requesting authorization decisions to the Policy Decision Point.
* **Policy Decision Point (PDP)**. It is a HTTPS server based on XACML. It accepts  XACML requests, which are attached into HTTP requests within the body. The PDP is contacted by the Capability Manager before generating a capability token for the Capability Client.
* **Policy Administration Point (PAP)**.
It is the entity responsible for managing the access control policies. It provides the functionality so users can define XACML policies in user-friendly way. The PAP has a GUI to facilitate the generation of XACML policies. 
