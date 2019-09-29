package com.axier.poke

import androidx.test.filters.SmallTest
import androidx.test.rule.ActivityTestRule
import com.axier.poke.data.repository.pokemon.PokemonListRepositoryImp
import com.axier.poke.data.repository.pokemon.PokemonRepositoryImp
import com.axier.poke.view.ui.SingleActivity
import org.jetbrains.anko.doAsync
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
@SmallTest
class ApiRequestTest {

    @get:Rule
    val activityRule = ActivityTestRule<SingleActivity>(SingleActivity::class.java, true, true)

    private  lateinit var pokemonsRepositoryImp: PokemonRepositoryImp
    private  lateinit var pokemonListRepositoryImp: PokemonListRepositoryImp

    @Before
    fun init() {
        pokemonsRepositoryImp = PokemonRepositoryImp(activityRule.activity)
        pokemonListRepositoryImp = PokemonListRepositoryImp(activityRule.activity)
    }

    @Test
    fun testGetPokemonList() {
        doAsync {
            val pokemons = pokemonListRepositoryImp.getPokemons(0)
            assertNotNull(pokemons)
            assert(pokemons?.results?.size!! > 0)
        }
    }

    @Test
    fun testGetPokemon() {
        doAsync {
            val pokemon = pokemonsRepositoryImp.getPokemon("pikachu")
            assertNotNull(pokemon)
            assertNotNull(pokemon?.name)
        }
    }
}
