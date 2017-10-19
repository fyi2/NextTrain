package org.sherman.tony.nexttrain.data

// Database (SQLite)

val DATABASE_VERSION : Int = 1
val DATABASE_NAME: String = "nexttrain.db"
val TABLE_NAME: String = "station"

// STATION table columns names
val KEY_ID: String = "id"
val KEY_STATION: String = "station_name"
val KEY_RECENT: String = "station_recent"
val KEY_FAVORITE: String = "station_favorite"


// SQL Commands
val CREATE_STATION_TABLE = "CREATE TABLE "+ TABLE_NAME + " ( "+ KEY_ID + " INTEGER PRIMARY KEY, "+
        KEY_STATION+" TEXT, "+
        KEY_FAVORITE+" INTEGER, "+
        KEY_RECENT+" LONG );"

val DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME
val SELECT_ALL = "SELECT * FROM " + TABLE_NAME


// Stations
val STATION_LIST: List<String>  = listOf("Abington","Anderson Woburn","Andover","Ashland","Attleboro","Auburndale","Ayer","Back Bay","Ballardvale","Bellevue",
"Belmont","Beverly","Beverly Farms","Boston Landing","Bradford","Braintree","Brandeis/Roberts","Bridgewater","Brockton",
"Buzzards Bay","Campello","Canton Center","Canton Junction","Chelsea","Cohasset","Concord","Dedham Corp Center",
"East Weymouth","Endicott","Fairmount","Fitchburg","Forest Hills","Forge Park 495","Four Corners/Geneva","Framingham",
"Franklin","Gloucester","Grafton","Greenbush","Greenwood","Halifax","Hamilton/Wenham","Hanson","Hastings","Haverhill","Hersey",
"Highland","Holbrook/Randolph","Hyannis","Hyde Park","Ipswich","Islington","JFK/UMASS","Kendal Green","Kingston","Lawrence",
"Lincoln","Littleton/Rte 495","Logan-E","Logan-Subway","Logan-A","Logan-Dock","Logan-B","Logan-C","Logan-RentalCarCenter",
"Lowell","Lynn","Malden Center","Manchester","Mansfield","Melrose Cedar Park","Melrose Highlands","Middleborough/Lakeville",
"Mishawum","Montello","Montserrat","Morton Street","Nantasket Junction","Natick Center","Needham Center","Needham Heights",
"Needham Junction","Newburyport","Newmarket","Newtonville","Norfolk","North Beverly","North Billerica","North Leominster",
"North Scituate","North Station","North Wilmington","Norwood Central","Norwood Depot","Plimptonville","Plymouth","Porter Square",
"Prides Crossing","Providence","Quincy Center","Reading","Readville","River Works","Rockport","Roslindale Village","Route 128",
"Rowley","Ruggles","Salem","Sharon","Shirley","Silver Hill","South Acton","South Attleboro","South Station","South Weymouth",
"Southborough","Stoughton","Swampscott","TF Green Airport","Talbot Avenue","Uphams Corner","Wachusett","Wakefield","Walpole",
"Waltham","Wareham Village","Waverley","Wedgemere","Wellesley Farms","Wellesley Hills","Wellesley Square","West Concord",
"West Gloucester","West Hingham","West Medford","West Natick","West Newton","West Roxbury","Westborough",
"Weymouth Landing/East Braintree","Whitman","Wickford Junction","Wilmington","Winchester Center","Windsor Gardens",
"Worcester","Wyoming Hill","Yawkey")

// Intent Codes
val CODE_STATION = 1
