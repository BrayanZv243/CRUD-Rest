package ws;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import entidades.Producto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.Map;

@Path("producto")
public class ProductoResource {

    private final Producto[] productos;

    private final Map<Integer, Producto> productosPorId;

    public ProductoResource() {
        productos = new Producto[]{
                new Producto(1, "Sabritas Gamesa"),
                new Producto(2, "Barritas Marinela"),
                new Producto(3, "Fritos con chorizo y chipotle")
        };
        productosPorId = new HashMap<>();
        for (Producto producto : productos) {
            productosPorId.put(producto.getId(), producto);
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public ApiResponse crearProducto(
            @FormParam("id") int id,
            @FormParam("nombre") String nombre) {
        Producto productoNuevo = new Producto(id, nombre);
        productosPorId.put(id, productoNuevo);
        return new ApiResponse(201, productos);
    }

    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public ApiResponse actualizarProducto(
            @FormParam("id") int id,
            @FormParam("nombre") String nombre) {
        Producto productoActualizado = new Producto(id, nombre);
        productosPorId.put(id, productoActualizado);
        return new ApiResponse(200, productos);
    }

    @GET
    @Path("{id}")
    public ApiResponse obtenerProducto(@PathParam("id") int id) {
        Producto producto = productosPorId.get(id);
        return new ApiResponse(200, producto);
    }

    @GET
    public ApiResponse obtenerProductos() {
        return new ApiResponse(200, productos);
    }

    @DELETE
    @Path("{id}")
    public ApiResponse borrarProducto(@PathParam("id") int id) {
        productosPorId.remove(id);
        return new ApiResponse(204, productos);
    }

    private static class ApiResponse {

        private final int status;

        private final Object entity;

        public ApiResponse(int status, Object entity) {
            this.status = status;
            this.entity = entity;
        }

        public int getStatus() {
            return status;
        }

        public Object getEntity() {
            return entity;
        }
    }
}
