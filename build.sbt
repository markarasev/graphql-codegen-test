import rocks.muki.graphql.codegen.CodeGenStyles

name := "graphqlCodegen-test"

version := "0.1"

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  "org.sangria-graphql" %% "sangria" % "1.3.0",
  "org.sangria-graphql" %% "sangria-circe" % "1.1.0"
)

enablePlugins(GraphQLSchemaPlugin, GraphQLQueryPlugin)
enablePlugins(GraphQLCodegenPlugin)

graphqlCodegenStyle := CodeGenStyles.Sangria

// add a 'starwars' schema to the `graphqlSchemas` list
graphqlSchemas += GraphQLSchema(
  "starwars",
  "starwars schema at http://try.sangria-graphql.org/graphql",
  Def.task(
    GraphQLSchemaLoader
      .fromIntrospection("http://try.sangria-graphql.org/graphql", streams.value.log)
      .withHeaders("User-Agent" -> s"sbt-graphql/${version.value}")
      .loadSchema()
  ).taskValue
)

// use this schema for the code generation
graphqlCodegenSchema := graphqlRenderSchema.toTask("starwars").value
