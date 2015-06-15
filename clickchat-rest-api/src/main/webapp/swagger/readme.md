#About the contents of this folder:

We have just copied the full contents of the 'dist' directory of the Swagger project, which contains all the 'static' Swagger UI (HTML,CSS,JS).

#### See:

[https://github.com/wordnik/swagger-ui](https://github.com/wordnik/swagger-ui)

### And what changes have we made?

Essentially we renamed Swagger's original index.html to index.jsp, so that we could add some logic.

In the first lines, we added:

```JSP
<%@page language="java" contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="req" value="${pageContext.request}" />
<c:set var="baseURL" value="${req.scheme}://${req.serverName}:${req.serverPort}${req.contextPath}/api-docs" />
```

After the standard JSP declarations, we set a variable that will contain Swagger's documentation URL. This is usually http://yourhost/api-docs.

Further, in the same file, we use such variable in following Javascript code:

```javascript
    $(function () {
      window.swaggerUi = new SwaggerUi({
      url: "${baseURL}",
      dom_id: "swagger-ui-container",
      supportedSubmitMethods: ['get', 'post', 'put', 'delete'],
      onComplete: function(swaggerApi, swaggerUi){
        log("Loaded SwaggerUI")
        $('pre code').each(function(i, e) {hljs.highlightBlock(e)});
      },
      onFailure: function(data) {
        log("Unable to Load SwaggerUI");
      },
      docExpansion: "none"
    });
```

Thus, Swagger's documentation URL will be automatically adjusted according to the environment where the application is deployed.

### And what should we do to update Swagger's UI when a new version is released?

Basically, all we have to do is to copy the 'dist' directory again to this folder and override the all the static content.
Then we have to rename index.html to index.jsp and edit the file once again, reproducing the changes shown above.