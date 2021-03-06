/*
fields_def.js: defines HashMap-like class and create map object to store field info

*/

function KeyValue( key, value )
{
    this.key = key;
    this.value = value;
}

function Map()
{
    this.array = new Array();
}

Map.prototype.put = function( key, value )
{
    if( ( typeof key != "undefined" ) && ( typeof value != "undefined" ) )
    {
        this.array[this.array.length] = new KeyValue( key, value );
    }
}

Map.prototype.get = function( key )
{
    for( var k = 0 ; k < this.array.length ; k++ )
    {
        if( this.array[k].key == key ) {
            return this.array[k].value;
        }
    }
    return null;
}

Map.prototype.length = function()
{
    return this.array.length;
}


var terms=new Map();
terms.put("pollinator", new Array("bee","beetle","bird","butterfly","fly","insect","mammal","moth","wasp","water","wind"));
terms.put("growth_form", new Array("herb",
									"shrub",
									"tree",
									"vine"));
terms.put("growth form", new Array("herb",
									"shrub",
									"tree",
									"vine"));									
									
terms.put("reproduction", new Array("no",
									"yes",
									"UNDEFINED"));
terms.put("si_mechanism", new Array("No",
									"Yes"));									
terms.put("floral_symmetry", new Array("bilateral",
										"radial"));
terms.put("sexual_system", new Array("androdioecious",
										"andromonoecious",
										"gynodioecious",
										"gynomonoecious",
										"hermophrodite",
										"heterostylous",
										"monoecious",
										"other",
										"varies"
										));
terms.put("biome", new Array("alpine/montane",
							 "aquatic (includes intertidal) both freshwater and...",
							 "arid shrubland",
							 "chapparal/Mediterranean",
							 "coastal (dunes)",
							 "desert",
							 "human disturbed",
							 "taiga/coniferous forest",
							 "temperate deciduous forest",
							 "temperate grassland",
							 "temperate rain forest",
							 "tropical rainforest",
							 "tropical savana",
							 "tropical seasonal forest"));
terms.put("life_history", new Array("annual",
									"biennial",
									"perennial and iteroparous",
									"variable monocarpic",
									"varies"));
terms.put("life history", new Array("annual",
									"biennial",
									"perennial and iteroparous",
									"variable monocarpic",
									"varies"));
terms.put("DevelopStage", new Array("EARLY: stage 1",
							"GERM: stage 2",
							"JUVENILE: stage 3",
							"ADULT: stage 4"
							));
							
							
terms.put("Environment", new Array("Common garden",
										"Field",
										"Greenhouse",
										"mixed:  plants were moved back and forth",
										"Undefined"));