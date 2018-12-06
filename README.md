# Loyalty Scheme Gambler

To run the app:

```
$ ./gradlew bootRun
```

The documentation will be generated from integration test cases when you execute:

```
$ ./gradlew bootJar
```

See build/asciidoc/html5/index.html for the generated, accurate API documentation.

If you want to play with the API, run up the app with the bootRun command and use curl to exercise the API (see
the generated documentation for examples).

## Requirements

* Client requires a simple marketing SPA where members can gamble their points on a dice roll.
* The type of game is variable, a game could be all-or-nothing e.g. if they roll an even number they double their points,
else the points are lost.
* Game would need to integrate with the loyalty scheme back-end systems using their JSON APIs.
* Must design some APIs that could do the job.
* Do just enough to cover the needs of the SPA.

## Decisions

* Follow Robert C. Martin's clean architecture (layers) in particular because the interface(s) that the dataprovider 
layer need to implement become our specification for an effective adapter (which will initially be a stub in the absence
of being provided with back-end system API documentation or a running copy of the said back-end system). Our external 
API (facing the SPA) which we'll call our entrypoint layer will be insulated from change at lower layers.
* We will park *some* authentication and authorization concerns but not park them entirely. For example, while we may not know
the specific technologies that may be used by the client, or available for use, we can still protect the back-end
APIs from abuse, or at least write interfaces to all stubs at critical points.
* We'll implement the API first leading to a service-oriented front end architecture (SOFEA) which is useful from 
a testing perspective irrespective of whether the front-end is an SPA or not.

## How did you decide upon your API design?

### Short Answer

I did not decide upon my API design as such, it was an incremental process over a number of days and I have 
a number of open questions and assumptions. For example I only just noticed that it appears I have two loyalty 
cards numbers from a particular scheme and am continuing to assume that from the loyalty scheme perspective these 
cannot be correlated to the same web login identifier (e.g. email address).

The design is likely to be incomplete because for example I have parked adding authorization and made business 
rule decisions that might turn out to be invalid. For example, I invented a rule that point balances are not allowed 
to become negative.

I did decide early on that presenting a working, well-documented API was not negotiable. So the one principal that I 
decided on was Code First which lends itself to incremental design.

I also decided that I wanted to demonstrate Spring REST Docs as either an alternative or supplement to
Swagger. This was to stimulate discussion on the general principal of generating accurate documentation for RESTful 
services by combining hand-written documentation with auto-generated snippets produced with test code, in this
case specifically using Spring MVC Test.

I did decide early on that the gaming logic and point management logic would be server-side because I thought it
would not be prudent to have this logic client-side (within the SPA's Javascript code). I did also decide, in my mind,
that the SPA would a a replaceable layer. I also parked performance concerns.

In summary the following were a given to me or implicit in what I did:

* Service-oriented front-end architecture (which goes well with SPAs)
* Accurate RESTful documentation generated from test cases and partly hand-written using asciidoc
* Cognizance of the Richardson Maturity Model
* Following the [Google JSON Style Guide](https://google.github.io/styleguide/jsoncstyleguide.xml)
* Thinking about statelessness
* Thinking about security concerns 
* Thinking where to place core business logic - and deciding that the core of the app would be server-side 
* I resisted the temptation to invent validation logic at all layers where there was significant uncertainty (e.g. the 
formatting of a valid loyalty scheme card number)

The following were open questions:

* Use of JSON Web Tokens and OAuth2 / OpenID Connect for authentication and authorization

### Long Answer

I had these thoughts and questions in the back of my mind to start with:

* REST and the Richardson Maturity Model
* A stateless back-end as seems to be the standard nowadays with OAuth 2 / OpenID Connect
* Park the details on how authentication and authorization will work to begin with but not at the points at which it 
should happen (e.g. add TODOs in code if need be)
* Do not expose or proxy the loyalty scheme back-end APIs to the SPA layer
* The need to build an *adaptable* adapter (dataprovider layer) used by the core layer is apparent.
* How to prevent *run-away* gambling, that is a coding error in the front-end (SPA) causing unwanted gambling events
in the back-end.
* Is this legal?
* Do key client stakeholders follow a "Design First" or "Code First" school of thought and approach to API development?

## How would you (or how did you) choose a language for the implementation?

### Short Answer

I have used what I'm learning at the moment to knock out an API.

In terms of would, if it were left to me I would not make a call without speaking to key stakeholders in particular 
those who'll be doing the bulk of the development and other key stakeholders. So in short I have not chosen a language
but described how I may decide on on one.


### Long Answer

There are likely to be multiple languages used unless opting for say Node.js on the backend and pure Javascript on the 
front-end. This combination would not be my first choice.

In terms of the backend (API) I'm teaching myself Kotlin at present and so have used Kotlin to the implement the SPA 
facing API. I also know Spring Boot well and enjoy using it, so have used Spring Boot. Spring Boot's testing 
infrastructure support is great and above all enjoyable to use. I would use a familiar thread-per request model as
used in Spring MVC, rather than Spring's new reactive programming model. The latter is because in the early days, with
unknowns in terms of requirements, a simpler programming model may trump performance concerns or say the amount of 
memory the app may require.

All of the above said, In terms of the actual backend implementation, I would talk to the client about their roadmaps 
and internal preferences before choosing a language. Chances are high that I would choose the client's primary language 
and frameworks if they decision was left to me. If the client had plans to use a language that is not heavily used in 
say Wellington or New Zealand then I may urge them to reconsider.

I have no strong feelings about the front-end language and framework(s) but would hope for one that has great testing
support and is heavily used (e.g. React or Angular). I would be curious as to whether the initial simple app
definitely needs to be an SPA.

## Would you (or did you) use any frameworks, and if so why?

### Short Answer

The answer is yes and no. 

Framework code is restricted to specific layers and the core of the back-end is free of 
framework code.

The why is because I don't believe in frameworks dictating the future of an application nor an engineers thought 
processes. It should be the other way around, I should be able to switch frameworks if need me.

These answers do not mean that I'm not pragmatic and will happily drop a layered architecture if need be, say when
rapid prototyping is called for.

### Long Answer

Yes I did use Spring Boot, Spring Web MVC and much more. 

No because no framework code is used at the core layer which is a central theme in Robert C. Martin's clean architecture
and something I have had to concentrate on after developing at a high pace for too long. I feel that frameworks dumb down 
developers (software engineers) and focusing on unit testing a core framework free layer is very important. It is easy to 
let frameworks "take over" with domain logic littered across a codebase which may make maintenance development difficult 
and expensive. This, "namely our framework does not come first", is also what Pivotal teaches developers on the Spring 
Core course. Moreover developing unit testable code takes thought, skill and effort and I think its a worthy investment 
(but it is equally important to know when *not* to focus on this).

The why covered in the long answer, but in short some key reasons why is because of the excellent teaching and tutorials 
provided by Pivotal, excellent test infrastructure support and it leads well onto using containers while still being 
able to use a debugger. Also because I know it very well and enjoy using it.

## How would you test this?

It depends heavily on the context. 

If the context demands a v1/v2 development process i.e. build v1 fast (i.e. rapid 
prototyping) and v2 right then the answer would differ from a "right first time" approach. It also depends on the team, 
business context and architecture. 

All of the above said I would use stock standard testing approaches as advocated by Spring at the Spring Boot app level, 
this is the RESful API level. When it comes to the SPA, I would use the approach used by the framework itself. Assuming 
we have a single docker container that hosts the API and SPA, there would need to be a test suite at this level too, 
then another test suite once we have deployed using say a blue-green deployment mechanism.

## Could you implement the marketing web site without access to an actual working API?

Yes, up to a point and the potential for re-work understood. 

With fantastic specifications and/or SDKs having an actual working API may not be necessary to complete the bulk of
the implementation *however* the implementation should never be regarded as complete without have performed 
integration testing using an actual working API. 

The marketing web site would in essence produce a *plugin* specification (an interface) that would need to be 
implemented by a plugin (in effect an adapter).

There is the risk that the cited interface would need to be changed somewhat once the actual working API (loyalty scheme 
backend API) becomes available for inspection and use.

Note excellent documentation could and should be supplied without an actual working API. Even better an OpenAPI
Specification (https://swagger.io/resources/articles/documenting-apis-with-swagger/) could be provided. Even better 
a documented SDK could be supplied (as AWS and many other web service providers do) in my favourite language.

*NOTE* knowing some aspects upfront could lead to significant time savings. For example, if I know OpenID Connect could
be used by the SPA because Single Sing On is already in use by the company, and I know that the JWT tokens can be 
relayed to the back-end loyalty scheme API for debit and credit authorization purposes then this is information that 
could and should be supplied (without access to an actual working API).

## If this needed to be turned into a stand-alone, Production ready artefact, how would you recommend it be deployed and managed?

The recommendation may change with time and involve trade-offs that are acceptable to various stakeholders who I have 
not had a chance to speak to.

That said, based on what I know now, which is only that the client uses AWS (probably the Sydney region) and has a small 
DevOps team, in terms of deployment I would opt for a *pure AWS* CD pipeline shipping a single container to begin with.

Developers and all an sundry should be able to run the container(s) and whole stack on their laptops at all times and
developers should be able to user mature, well known and understood tooling (e.g. a familiar IDE and debugger).

- [reference blog](https://aws.amazon.com/blogs/compute/set-up-a-continuous-delivery-pipeline-for-containers-using-aws-codepipeline-and-amazon-ecs/)
- [reference tut](https://docs.aws.amazon.com/AmazonECS/latest/developerguide/ecs-cd-pipeline.html)

In terms of the managed part, ideally there would be a squad that is responsible for the pipeline and no silo-ing. That 
is, each squad member would be able to stand in for each other on the AWS front, Kotlin front or front-end front.

Also, I would gradually enhance the capabilities of the pipeline and park e.g. blue/green deployment concerns.

I would consider parking using a container based pipeline entirely, and just using a PaaS (e.g. ElasticBeanStalk). The 
reason for this is because AWS concerns and even a *simple* CD pipeline can turn on to be a significant investment and 
require hungry individuals.
