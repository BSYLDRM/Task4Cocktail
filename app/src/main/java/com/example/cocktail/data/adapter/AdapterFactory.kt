package com.example.cocktail.data.adapter

object AdapterFactory {
    fun createAdapter(type: AdapterType, clickListener: (Any) -> Unit): GenericAdapter {
        return GenericAdapter(emptyList(), type, clickListener)
    }
}