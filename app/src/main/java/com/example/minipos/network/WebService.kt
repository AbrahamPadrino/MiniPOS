package com.example.minipos.network

import com.example.minipos.models.Producto
import com.example.minipos.models.Proveedor
import com.example.minipos.models.Usuario
import com.example.minipos.models.VentasSend
import com.example.minipos.response.LoginResponse
import com.example.minipos.response.ProductoResponse
import com.example.minipos.response.ProveedorResponse
import com.example.minipos.response.VentaResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface WebService {

    // LOGIN
    @POST("/login")
    suspend fun login(
        @Body usuario: Usuario
    ): Response<LoginResponse>
    // END LOGIN

    // PRODUCTOS
    @GET("/productos")
    suspend fun obtenerProductos()
    : Response<ProductoResponse>

    @GET("/productos/{codProducto}")
    suspend fun obtenerProducto(
        @Path("codProducto") codProducto: String
    ): Response<ProductoResponse>

    @POST("/productos/add")
    suspend fun agregarProducto(
        @Body prod: Producto
    ): Response<ProductoResponse>

    @PUT("/productos/update/{codProducto}")
    suspend fun actualizarProducto(
        @Path("codProducto") codProducto: String,
        @Body prod: Producto
    ): Response<ProductoResponse>

    @DELETE("/productos/delete/{codProducto}")
    suspend fun borrarProducto(
        @Path("codProducto") codProducto: String
    ): Response<ProductoResponse>
    // END PRODUCTOS



    // PROVEEDORES
    @GET("/proveedores")
    suspend fun obtenerProveedores()
    : Response<ProveedorResponse>

    @POST("/proveedores/add")
    suspend fun agregarProveedor(
        @Body prov: Proveedor
    ): Response<ProveedorResponse>

    @PUT("/proveedores/update/{nomProveedor}")
    suspend fun actualizarProveedor(
        @Path("nomProveedor") nomProveedor: String,
        @Body prov: Proveedor
    ): Response<ProveedorResponse>
    // END PROVEEDORES



    // VENTAS
    @POST("/ventas/add")
    suspend fun agregarVenta(
        @Body datosSend: VentasSend
    ): Response<VentaResponse>
    // END VENTAS



    // PERIODOS
    @GET("/ventas/periodo")
    suspend fun obtenerVentasPeriodo(
        @Query("fechaInicio") fechaInicio: String,
        @Query("fechaFinal") fechaFinal: String
    ): Response<VentaResponse>
    // END PERIODOS
}