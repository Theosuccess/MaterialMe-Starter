
package com.example.android.materialme;

/**
 * Data model for each row of the RecyclerView.
 */
class Sport {

    //Member variables representing the title, information and imageResource about the sport
    private String title;
    private String info;
    private final int imageResource;

    /**
     * Constructor for the Sport data model
     * @param title The name of the sport.
     * @param info Information about the sport.
     * @param imageResource
     *
     */
    Sport(String title, String info, int imageResource) {
        this.title = title;
        this.info = info;
        this.imageResource = imageResource;
    }

    /**
     * Gets the title of the sport
     * @return The title of the sport.
     */
    String getTitle() {
        return title;
    }
    /**
     * Gets the info about the sport
     * @return The info about the sport.
     */
    String getInfo() {
        return info;
    }
    //Create a getter for the imageResource integer:
     //@return The imageResource of the sport
    int getImageResource(){
        return imageResource;
    }
}
