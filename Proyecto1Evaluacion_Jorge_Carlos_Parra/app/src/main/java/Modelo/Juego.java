package Modelo;

public class Juego {
    String id, name, ImageUrl, description, genero, plataforma;

    public Juego(){

    }

    public  Juego(String id, String name, String ImageUrl, String description, String genero, String plataforma){
        this.id = id;
        this.name = name;
        this.ImageUrl = ImageUrl;
        this.description = description;
        this.genero = genero;
        this.plataforma = plataforma;
    }

    public String getId(){
        return id;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public String getName(){
        return name;
    }

    public String getImageUrl(){
        return ImageUrl;
    }

    public String getDescription(){
        return description;
    }

    public String getGenero(){
        return genero;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
