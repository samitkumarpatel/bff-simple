# bff-simple

This simple example demonstrates how to use the BFF (Backend For Frontend) pattern to create a simple API that aggregates data from multiple sources.

It also has ann example of how a oath2-client can be used to authenticate with spring oauth2 authorization server.

In the `authorization-server` there are total three clients configured.
1. oidc-client
2. webflux-client
3. api-gateway-client

The `oidc-client` and `webflux-client` are used in `oauth2-client` example.

To showcase this example , start `authorization-server` and `oauth2-client`

To see if the oauth2 client endpoint are working as expected, you can use the `/` and `/webflux` to get the access token.


and `api-gateway` is used in in `api-gateway` example to demonstrate `bff`.

To showcase this example , start `authorization-server` and `oauth2-client` services along with a simple webserver expose on a port `4200`.

I'm using `jwebserver` to serve the static content. You can use any other webserver of your choice like nginx, nodejs and etc.

```shell
jwebserver -p 4200
```

In the api-gateway there are several endpoints configured to demonstrate the BFF pattern like `/` ,  `/spa`, `/ui` and `/frontend`.