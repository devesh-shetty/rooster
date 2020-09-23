package com.deveshshetty.rooster.graphql.hook

import com.expediagroup.graphql.hooks.SchemaGeneratorHooks
import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.GraphQLScalarType
import graphql.schema.GraphQLType
import java.time.LocalDate
import kotlin.reflect.KClass
import kotlin.reflect.KType

class CustomSchemaGeneratorHooks : SchemaGeneratorHooks {
    override fun willGenerateGraphQLType(type: KType): GraphQLType? = when (type.classifier as? KClass<*>) {
        LocalDate::class -> graphqlDateType
        else -> null
    }
}

val graphqlDateType: GraphQLScalarType = GraphQLScalarType.newScalar()
        .name("Date")
        .description("A type representing date (yyyy-mm-dd)")
        .coercing(LocalDateCoercing)
        .build()

object LocalDateCoercing : Coercing<LocalDate, String> {
    override fun parseValue(input: Any?): LocalDate = LocalDate.parse(serialize(input))

    override fun parseLiteral(input: Any?): LocalDate? {
        val uuidString = (input as? StringValue)?.value
        return LocalDate.parse(uuidString)
    }

    override fun serialize(dataFetcherResult: Any?): String = dataFetcherResult.toString()
}