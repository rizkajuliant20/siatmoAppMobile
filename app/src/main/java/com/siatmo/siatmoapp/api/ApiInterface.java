package com.siatmo.siatmoapp.api;

import com.siatmo.siatmoapp.modul.CabangDAO;
import com.siatmo.siatmoapp.modul.CustomerBikeDAO;
import com.siatmo.siatmoapp.modul.CustomerDAO;
import com.siatmo.siatmoapp.modul.DetailPemesananSpaDAO;
import com.siatmo.siatmoapp.modul.DetailPenjualanJasaDAO;
import com.siatmo.siatmoapp.modul.DetailPenjualanSparepart;
import com.siatmo.siatmoapp.modul.JSONResponse;
import com.siatmo.siatmoapp.modul.JSONResponseSingle;
import com.siatmo.siatmoapp.modul.JasaServiceDAO;
import com.siatmo.siatmoapp.modul.MontirOnDutyDAO;
import com.siatmo.siatmoapp.modul.PegawaiDAO;
import com.siatmo.siatmoapp.modul.PemesananSparepartDAO;
import com.siatmo.siatmoapp.modul.PenjualanDAO;
import com.siatmo.siatmoapp.modul.PosisiDAO;
import com.siatmo.siatmoapp.modul.SparepartDAO;
import com.siatmo.siatmoapp.modul.SparepartMotorDAO;
import com.siatmo.siatmoapp.modul.SupplierDAO;
import com.siatmo.siatmoapp.modul.TipeMotorDAO;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiInterface {

    //=======================Login================================================================
    @POST("mobileauth")
    @FormUrlEncoded
    Call<ResponseBody> GetLogin(
            @Field("username")String username,
            @Field("password")String password);

    //=======================Supplier=============================================================
    @GET("suppliers")
    Call<List<SupplierDAO>> getSupplier();


    @POST("suppliers")
    @FormUrlEncoded
    Call<SupplierDAO> createData (@Field("NAMA_SUPPLIER") String NAMA_SUPPLIER,
                                  @Field("ALAMAT_SUPPLIER") String ALAMAT_SUPPLIER,
                                  @Field("TELEPON_SUPPLIER") String TELEPON_SUPPLIER,
                                  @Field("NAMA_SALES") String NAMA_SALES,
                                  @Field("TELEPON_SALES") String TELEPON_SALES);


    @PUT("suppliers/{ID_SUPPLIER}")
    @FormUrlEncoded
    Call<SupplierDAO> editData (@Path("ID_SUPPLIER") int ID_SUPPLIER,
                                  @Field("NAMA_SUPPLIER") String NAMA_SUPPLIER,
                                  @Field("ALAMAT_SUPPLIER") String ALAMAT_SUPPLIER,
                                  @Field("TELEPON_SUPPLIER") String TELEPON_SUPPLIER,
                                  @Field("NAMA_SALES") String NAMA_SALES,
                                  @Field("TELEPON_SALES") String TELEPON_SALES);


    @GET("suppliers/{ID_SUPPLIER}")
    @FormUrlEncoded
    Call<SupplierDAO> readData (@Path("ID_SUPPLIER") int ID_SUPPLIER,
                                @Field("NAMA_SUPPLIER") String NAMA_SUPPLIER,
                                @Field("ALAMAT_SUPPLIER") String ALAMAT_SUPPLIER,
                                @Field("TELEPON_SUPPLIER") String TELEPON_SUPPLIER,
                                @Field("NAMA_SALES") String NAMA_SALES,
                                @Field("TELEPON_SALES") String TELEPON_SALES);


    @DELETE("suppliers/{ID_SUPPLIER}")
    Call<SupplierDAO> deleteData (@Path("ID_SUPPLIER") int ID_SUPPLIER);

    //====================Sparepart=====================================================
    @GET("spareparts")
    Call<JSONResponse> getSpareparts();

    @POST("spareparts")
    @FormUrlEncoded
    Call<JSONResponseSingle> createDataSpa (@Field("ID_SPAREPARTS") String ID_SPAREPARTS,
                                            @Field("KODE_PENEMPATAN") String KODE_PENEMPATAN,
                                            @Field("NAMA_SPAREPART") String NAMA_SPAREPART,
                                            @Field("HARGA_BELI") Double HARGA_BELI,
                                            @Field("HARGA_JUAL") Double HARGA_JUAL,
                                            @Field("STOK_MINIMAL") int STOK_MINIMAL,
                                            @Field("STOK_BARANG") int STOK_BARANG,
                                            @Field("GAMBAR") String GAMBAR,
                                            @Field("TIPE") String TIPE);

    @POST("spareparts")
    @Multipart
    Call<JSONResponseSingle> createDataSpaMulti (@Part("ID_SPAREPARTS") RequestBody ID_SPAREPARTS,
                                            @Part("KODE_PENEMPATAN") RequestBody KODE_PENEMPATAN,
                                            @Part("NAMA_SPAREPART") RequestBody NAMA_SPAREPART,
                                            @Part("HARGA_BELI") RequestBody HARGA_BELI,
                                            @Part("HARGA_JUAL") RequestBody HARGA_JUAL,
                                            @Part("STOK_MINIMAL") RequestBody STOK_MINIMAL,
                                            @Part("STOK_BARANG") RequestBody STOK_BARANG,
                                            @Part MultipartBody.Part GAMBAR,
                                            @Part("TIPE") RequestBody TIPE);


    @FormUrlEncoded
    @PUT("spareparts/{ID_SPAREPARTS}")
    Call<JSONResponseSingle> editDataSpa (@Path("ID_SPAREPARTS") String ID_SPAREPARTS,
                                          @Field("KODE_PENEMPATAN") String KODE_PENEMPATAN,
                                          @Field("NAMA_SPAREPART") String NAMA_SPAREPART,
                                          @Field("HARGA_BELI") Double HARGA_BELI,
                                          @Field("HARGA_JUAL") Double HARGA_JUAL,
                                          @Field("STOK_MINIMAL") int STOK_MINIMAL,
                                          @Field("STOK_BARANG") int STOK_BARANG,
                                          @Field("GAMBAR") String GAMBAR,
                                          @Field("TIPE") String TIPE);

    @FormUrlEncoded
    @GET("spareparts/{ID_SPAREPARTS}")
    Call<JSONResponseSingle> readDataSpa (@Path("ID_SPAREPARTS") String ID_SPAREPARTS,
                                          @Field("KODE_PENEMPATAN") String KODE_PENEMPATAN,
                                          @Field("NAMA_SPAREPART") String NAMA_SPAREPART,
                                          @Field("HARGA_BELI") Double HARGA_BELI,
                                          @Field("HARGA_JUAL") Double HARGA_JUAL,
                                          @Field("STOK_MINIMAL") int STOK_MINIMAL,
                                          @Field("STOK_BARANG") int STOK_BARANG,
                                          @Field("GAMBAR") String GAMBAR,
                                          @Field("TIPE") String TIPE);

    @DELETE("spareparts/{ID_SPAREPARTS}")
    Call<JSONResponseSingle> deleteDataSpa (@Path("ID_SPAREPARTS") String ID_SPAREPARTS);

    @Multipart
    @POST("updateImageMobile")
    Call<JSONResponseSingle> uploadGambar (@Part("ID_SPAREPARTS") RequestBody ID_SPAREPARTS,
                                           @Part MultipartBody.Part GAMBAR);

    //====================Posisi==================================================================

    @GET("posisi")
    Call<List<PosisiDAO>> getPosisi();

    //====================Service==================================================================

    @GET("services")
    Call<List<JasaServiceDAO>> getService();

    //====================Motor==================================================================

    @GET("motors")
    Call<List<TipeMotorDAO>> getMotors();

    @GET("motors")
    Call<ArrayList<TipeMotorDAO>> getMotorsArray();

    @FormUrlEncoded
    @POST("motors")
    Call<TipeMotorDAO> createDataMotor (@Field("TIPE_MOTOR") String TIPE_MOTOR,
                                        @Field("MERK_MOTOR") String MERK_MOTOR);

    @FormUrlEncoded
    @PUT("motors/{ID_MOTOR}")
    Call<TipeMotorDAO> editDataMotor (@Path("ID_MOTOR") int ID_MOTOR,
                                      @Field("TIPE_MOTOR") String TIPE_MOTOR,
                                      @Field("MERK_MOTOR") String MERK_MOTOR);

    @FormUrlEncoded
    @GET("motors/{ID_MOTOR}")
    Call<TipeMotorDAO> readDataMotor (@Path("ID_MOTOR") int ID_SUPPLIER,
                                      @Field("TIPE_MOTOR") String TIPE_MOTOR,
                                      @Field("MERK_MOTOR") String MERK_MOTOR);


    @DELETE("motors/{ID_MOTOR}")
    Call<TipeMotorDAO> deleteDataMotor (@Path("ID_MOTOR") int ID_MOTOR);

    //====================Cabang=========================================================
    @GET("branches")
    Call<List<CabangDAO>> getCabangs();

    @FormUrlEncoded
    @POST("branches")
    Call<CabangDAO> createDataCabang (@Field("NAMA_CABANG") String NAMA_CABANG,
                                     @Field("ALAMAT_CABANG") String ALAMAT_CABANG,
                                     @Field("TELEPON_CABANG") String TELEPON_CABANG);

    @FormUrlEncoded
    @PUT("branches/{ID_CABANG}")
    Call<CabangDAO> editDataCabang (@Path("ID_CABANG") int ID_CABANG,
                                      @Field("NAMA_CABANG") String NAMA_CABANG,
                                      @Field("ALAMAT_CABANG") String ALAMAT_CABANG,
                                      @Field("TELEPON_CABANG") String TELEPON_CABANG);

    @FormUrlEncoded
    @GET("branches/{ID_CABANG}")
    Call<CabangDAO> readDataCabang (@Path("ID_CABANG") int ID_CABANG,
                                      @Field("NAMA_CABANG") String NAMA_CABANG,
                                      @Field("ALAMAT_CABANG") String ALAMAT_CABANG,
                                      @Field("TELEPON_CABANG") String TELEPON_CABANG);


    @DELETE("branches/{ID_CABANG}")
    Call<CabangDAO> deleteDataCabang (@Path("ID_CABANG") int ID_CABANG);

    //====================Pelanggan=========================================================
    @GET("pelanggan")
    Call<List<CustomerDAO>> getCustomer();

    @FormUrlEncoded
    @POST("pelanggan")
    Call<CustomerDAO> createDataCustomer (@Field("NAMA_PELANGGAN") String NAMA_PELANGGAN,
                                      @Field("ALAMAT_PELANGGAN") String ALAMAT_PELANGGAN,
                                      @Field("TELEPON_PELANGGAN") String TELEPON_PELANGGAN);

    @FormUrlEncoded
    @PUT("pelanggan/{ID_PELANGGAN}")
    Call<CustomerDAO> editDataCustomer (@Path("ID_PELANGGAN") int ID_PELANGGAN,
                                    @Field("NAMA_PELANGGAN") String NAMA_PELANGGAN,
                                    @Field("ALAMAT_PELANGGAN") String ALAMAT_PELANGGAN,
                                    @Field("TELEPON_PELANGGAN") String TELEPON_PELANGGAN);

    @FormUrlEncoded
    @GET("pelanggan/{ID_PELANGGAN}")
    Call<CustomerDAO> readDataCustomer (@Path("ID_PELANGGAN") int ID_PELANGGAN,
                                    @Field("NAMA_PELANGGAN") String NAMA_PELANGGAN,
                                    @Field("ALAMAT_PELANGGAN") String ALAMAT_PELANGGAN,
                                    @Field("TELEPON_PELANGGAN") String TELEPON_PELANGGAN);


    @DELETE("pelanggan/{ID_PELANGGAN}")
    Call<CustomerDAO> deleteDataCustomer (@Path("ID_PELANGGAN") int ID_PELANGGAN);

    //====================Kendaraan Pelanggan=========================================================
    @GET("kendaraanPelanggan")
    Call<List<CustomerBikeDAO>> getCustomerBike();

    @FormUrlEncoded
    @POST("kendaraanPelanggan")
    Call<CustomerBikeDAO> createDataCustomerBike (@Field("ID_MOTOR") int ID_MOTOR,
                                          @Field("ID_PELANGGAN") int ID_PELANGGAN,
                                          @Field("NO_PLAT") String NO_PLAT);

    @FormUrlEncoded
    @PUT("kendaraanPelanggan/{ID_KENDARAAN_PEL}")
    Call<CustomerBikeDAO> editDataCustomerBike (@Path("ID_KENDARAAN_PEL") int ID_KENDARAAN_PEL,
                                        @Field("ID_MOTOR") int ID_MOTOR,
                                        @Field("ID_PELANGGAN") int ID_PELANGGAN,
                                        @Field("NO_PLAT") String NO_PLAT);

    @FormUrlEncoded
    @GET("kendaraanPelanggan/{ID_KENDARAAN_PEL}")
    Call<CustomerBikeDAO> readDataCustomerBike (@Path("ID_KENDARAAN_PEL") int ID_KENDARAAN_PEL,
                                        @Field("ID_MOTOR") int ID_MOTOR,
                                        @Field("ID_PELANGGAN") int ID_PELANGGAN,
                                        @Field("NO_PLAT") String NO_PLAT);

    @DELETE("kendaraanPelanggan/{ID_KENDARAAN_PEL}")
    Call<CustomerBikeDAO> deleteDataCustomerBike (@Path("ID_KENDARAAN_PEL") int ID_KENDARAAN_PEL);

    //====================Kecocokan Sparepart dan Tipe Motor=========================================================
    @GET("sparepartMotor")
    Call<List<SparepartMotorDAO>> getSpaMotor();

    @FormUrlEncoded
    @POST("sparepartMotor")
    Call<SparepartMotorDAO> createDataSpaMotor (@Field("ID_SPAREPARTS") String ID_SPAREPARTS,
                                                  @Field("ID_MOTOR") int ID_MOTOR);

    @FormUrlEncoded
    @PUT("sparepartMotor/{ID_SPAREPART_MOTOR}")
    Call<SparepartMotorDAO> editDataSpaMotor (@Path("ID_SPAREPART_MOTOR") int ID_SPAREPART_MOTOR,
                                            @Field("ID_SPAREPARTS") String ID_SPAREPARTS,
                                            @Field("ID_MOTOR") int ID_MOTOR);

    @FormUrlEncoded
    @GET("sparepartMotor/{ID_SPAREPART_MOTOR}")
    Call<SparepartMotorDAO> readDataSpaMotor (@Path("ID_SPAREPART_MOTOR") int ID_SPAREPART_MOTOR,
                                            @Field("ID_SPAREPARTS") String ID_SPAREPARTS,
                                            @Field("ID_MOTOR") int ID_MOTOR);


    @DELETE("sparepartMotor/{ID_SPAREPART_MOTOR}")
    Call<SparepartMotorDAO> deleteDataSpaMotor (@Path("ID_SPAREPART_MOTOR") int ID_SPAREPART_MOTOR);

    @DELETE("sparepartMotor/{ID_SPAREPARTS}/{ID_MOTOR}")
    Call<SparepartMotorDAO> hapusSpaMotor (@Path ("ID_SPAREPARTS") String ID_SPAREPARTS,
                                           @Path ("ID_MOTOR") int ID_MOTOR);

    //====================Pemesanan Sparepart=========================================================
    @GET("pemesanan")
    Call<List<PemesananSparepartDAO>> getPemesanan();

    @FormUrlEncoded
    @POST("pemesanan")
    Call<PemesananSparepartDAO> createDataPesanan (@Field("ID_SUPPLIER") int ID_SUPPLIER,
                                                @Field("TGL_PEMESANAN") String TGL_PEMESANAN,
                                                @Field("GRANDTOTAL_PEMESANAN") double GRANDTOTAL_PEMESANAN,
                                                @Field("STATUS_PEMESANAN") String STATUS_PEMESANAN);

    @FormUrlEncoded
    @PUT("pemesanan/{ID_PEMESANAN}")
    Call<PemesananSparepartDAO> editDataPesanan (@Path("ID_PEMESANAN") int ID_PEMESANAN,
                                                 @Field("ID_PEMESANAN") int id_pesan,
                                              @Field("ID_SUPPLIER") int ID_SUPPLIER,
                                              @Field("TGL_PEMESANAN") String TGL_PEMESANAN,
                                              @Field("GRANDTOTAL_PEMESANAN") double GRANDTOTAL_PEMESANAN,
                                              @Field("STATUS_PEMESANAN") String STATUS_PEMESANAN);

    @FormUrlEncoded
    @GET("pemesanan/{ID_PEMESANAN}")
    Call<PemesananSparepartDAO> readDataPesanan (@Path("ID_PEMESANAN") int ID_PEMESANAN,
                                              @Field("ID_SUPPLIER") int ID_SUPPLIER,
                                              @Field("TGL_PEMESANAN") String TGL_PEMESANAN,
                                              @Field("GRANDTOTAL_PEMESANAN") double GRANDTOTAL_PEMESANAN,
                                              @Field("STATUS_PEMESANAN") String STATUS_PEMESANAN);


    @DELETE("pemesanan/{ID_PEMESANAN}")
    Call<PemesananSparepartDAO> deleteDataPesanan (@Path("ID_PEMESANAN") int ID_PEMESANAN);

    //====================Detail Pemesanan Sparepart=========================================================
    @GET("detailPemesanan")
    Call<List<DetailPemesananSpaDAO>> getDetailPemesanan();

    @FormUrlEncoded
    @POST("detailPemesanan")
    Call<DetailPemesananSpaDAO> createDataDetailPesanan (@Field("ID_SPAREPARTS") String ID_SPAREPARTS,
                                                         @Field("ID_PEMESANAN") int ID_PEMESANAN,
                                                         @Field("JUMLAH_PEMESANAN") int JUMLAH_PEMESANAN,
                                                         @Field("HARGA_BELI_PEMESANAN") double HARGA_BELI_PEMESANAN,
                                                         @Field("SATUAN") String SATUAN);

    @FormUrlEncoded
    @PUT("detailPemesanan/{ID_DETAIL_PEMESANAN}")
    Call<DetailPemesananSpaDAO> editDataDetailPesanan ( @Path("ID_DETAIL_PEMESANAN") int ID_DETAIL_PEMESANAN,
                                                        @Field("ID_SPAREPARTS") String ID_SPAREPARTS,
                                                        @Field("ID_PEMESANAN") int ID_PEMESANAN,
                                                        @Field("JUMLAH_PEMESANAN") int JUMLAH_PEMESANAN,
                                                        @Field("HARGA_BELI_PEMESANAN") double HARGA_BELI_PEMESANAN,
                                                        @Field("SATUAN") String SATUAN);

    @FormUrlEncoded
    @GET("detailPemesanan/{ID_DETAIL_PEMESANAN}")
    Call<DetailPemesananSpaDAO> readDataDetailPesanan (@Path("ID_DETAIL_PEMESANAN") int ID_DETAIL_PEMESANAN,
                                                       @Field("ID_SPAREPARTS") String ID_SPAREPARTS,
                                                       @Field("ID_PEMESANAN") int ID_PEMESANAN,
                                                       @Field("JUMLAH_PEMESANAN") int JUMLAH_PEMESANAN,
                                                       @Field("HARGA_BELI_PEMESANAN") double HARGA_BELI_PEMESANAN,
                                                       @Field("SATUAN") String SATUAN);

    @DELETE("detailPemesanan/{ID_DETAIL_PEMESANAN}")
    Call<DetailPemesananSpaDAO> deleteDataDetailPesanan (@Path("ID_DETAIL_PEMESANAN") int ID_DETAIL_PEMESANAN);

    @DELETE("detailPemesanan/{ID_PEMESANAN}/{ID_SPAREPARTS}")
    Call<DetailPemesananSpaDAO> deleteDataDetailTertentu (@Path("ID_PEMESANAN") int ID_PEMESANAN,
                                                         @Path("ID_SPAREPARTS") String ID_SPAREPARTS);

    //====================Transaksi Penjualan=========================================================
    @GET("detailPenjualanJasa")
    Call<List<DetailPenjualanJasaDAO>> getDetailJasa();

    @FormUrlEncoded
    @POST("detailPenjualanJasa")
    Call<DetailPenjualanJasaDAO> createDataDetailJasa (@Field("ID_TRANSAKSI") String ID_TRANSAKSI,
                                                         @Field("ID_JASA") int ID_JASA,
                                                         @Field("ID_MONTIR_ONDUTY") int ID_MONTIR_ONDUTY,
                                                         @Field("SUBTOTAL_JASA") double SUBTOTAL_JASA,
                                                         @Field("STATUS_JASA") String STATUS_JASA);

    @FormUrlEncoded
    @PUT("detailPenjualanJasa/{ID_DETAIL_PENJUALAN_JASA}")
    Call<DetailPenjualanJasaDAO> editDataDetailJasa ( @Path("ID_DETAIL_PENJUALAN_JASA") int ID_DETAIL_PENJUALAN_JASA,
                                                      @Field("ID_TRANSAKSI") String ID_TRANSAKSI,
                                                      @Field("ID_JASA") int ID_JASA,
                                                      @Field("ID_MONTIR_ONDUTY") int ID_MONTIR_ONDUTY,
                                                      @Field("SUBTOTAL_JASA") double SUBTOTAL_JASA,
                                                      @Field("STATUS_JASA") String STATUS_JASA);

    @FormUrlEncoded
    @GET("detailPenjualanJasa/{ID_DETAIL_PENJUALAN_JASA}")
    Call<DetailPenjualanJasaDAO> readDataDetailJasa (@Path("ID_DETAIL_PENJUALAN_JASA") int ID_DETAIL_PENJUALAN_JASA,
                                                     @Field("ID_TRANSAKSI") String ID_TRANSAKSI,
                                                     @Field("ID_JASA") int ID_JASA,
                                                     @Field("ID_MONTIR_ONDUTY") int ID_MONTIR_ONDUTY,
                                                     @Field("SUBTOTAL_JASA") double SUBTOTAL_JASA,
                                                     @Field("STATUS_JASA") String STATUS_JASA);

    @DELETE("detailPenjualanJasa/{ID_DETAIL_PENJUALAN_JASA}")
    Call<DetailPenjualanJasaDAO> deleteDataDetailJasa (@Path("ID_DETAIL_PENJUALAN_JASA") int ID_DETAIL_PENJUALAN_JASA);

    //====================Detail Penjualan Sparepart=========================================================
    @GET("detailPenjualanSparepart")
    Call<List<DetailPenjualanSparepart>> getDetailSparepart();

    @FormUrlEncoded
    @POST("detailPenjualanSparepart")
    Call<DetailPenjualanSparepart> createDataDetailSparepart(@Field("ID_TRANSAKSI") String ID_TRANSAKSI,
                                                             @Field("ID_SPAREPARTS") String ID_SPAREPARTS,
                                                             @Field("JUMLAH_SPAREPART") int JUMLAH_SPAREPART,
                                                             @Field("SUBTOTAL_SPAREPART") double SUBTOTAL_SPAREPART,
                                                             @Field("HARGA_TAMPUNG_JUAL") double HARGA_TAMPUNG_JUAL);

    @FormUrlEncoded
    @PUT("detailPenjualanSparepart/{ID_PENJUALAN_SPAREPART}")
    Call<DetailPenjualanSparepart> editDataDetailSparepart ( @Path("ID_PENJUALAN_SPAREPART") int ID_PENJUALAN_SPAREPART,
                                                             @Field("ID_TRANSAKSI") String ID_TRANSAKSI,
                                                             @Field("ID_SPAREPARTS") String ID_SPAREPARTS,
                                                             @Field("JUMLAH_SPAREPART") int JUMLAH_SPAREPART,
                                                             @Field("SUBTOTAL_SPAREPART") double SUBTOTAL_SPAREPART,
                                                             @Field("HARGA_TAMPUNG_JUAL") double HARGA_TAMPUNG_JUAL);

    @FormUrlEncoded
    @GET("detailPenjualanSparepart/{ID_PENJUALAN_SPAREPART}")
    Call<DetailPenjualanSparepart> readDataDetailSparepart (@Path("ID_PENJUALAN_SPAREPART") int ID_PENJUALAN_SPAREPART,
                                                            @Field("ID_TRANSAKSI") String ID_TRANSAKSI,
                                                            @Field("ID_SPAREPARTS") String ID_SPAREPARTS,
                                                            @Field("JUMLAH_SPAREPART") int JUMLAH_SPAREPART,
                                                            @Field("SUBTOTAL_SPAREPART") double SUBTOTAL_SPAREPART,
                                                            @Field("HARGA_TAMPUNG_JUAL") double HARGA_TAMPUNG_JUAL);

    @DELETE("detailPenjualanSparepart/{ID_PENJUALAN_SPAREPART}")
    Call<DetailPenjualanSparepart> deleteDataDetailSparepart (@Path("ID_PENJUALAN_SPAREPART") int ID_PENJUALAN_SPAREPART);

    //====================Transaksi Penjualan=========================================================
    @GET("transaksiPenjualan")
    Call<List<PenjualanDAO>> getTransaksiPenjualan();

    @FormUrlEncoded
    @POST("transaksiPenjualan")
    Call<PenjualanDAO> createDataTransaksiPenjualan(@Field("ID_TRANSAKSI") String ID_TRANSAKSI,
                                                    @Field("ID_CABANG") int ID_CABANG,
                                                    @Field("ID_PELANGGAN") int ID_PELANGGAN,
                                                    @Field("TGL_TRANSAKSI") String TGL_TRANSAKSI,
                                                    @Field("SUBTOTAL") double SUBTOTAL,
                                                    @Field("DISKON") double DISKON,
                                                    @Field("GRANDTOTAL") double GRANDTOTAL,
                                                    @Field("STATUS_TRANSAKSI") String STATUS_TRANSAKSI,
                                                    @Field("JENIS_TRANSAKSI") String JENIS_TRANSAKSI);

    @FormUrlEncoded
    @PUT("transaksiPenjualan/{ID_TRANSAKSI}")
    Call<PenjualanDAO> editDataTransaksiPenjualan( @Path("ID_TRANSAKSI") String ID_TRANSAKSI,
                                                   @Field("ID_CABANG") int ID_CABANG,
                                                   @Field("ID_PELANGGAN") int ID_PELANGGAN,
                                                   @Field("TGL_TRANSAKSI") String TGL_TRANSAKSI,
                                                   @Field("SUBTOTAL") double SUBTOTAL,
                                                   @Field("DISKON") double DISKON,
                                                   @Field("GRANDTOTAL") double GRANDTOTAL,
                                                   @Field("STATUS_TRANSAKSI") String STATUS_TRANSAKSI,
                                                   @Field("JENIS_TRANSAKSI") String JENIS_TRANSAKSI);

    @FormUrlEncoded
    @GET("transaksiPenjualan/{ID_TRANSAKSI}")
    Call<PenjualanDAO> readDataTransaksiPenjualan(@Path("ID_TRANSAKSI") String ID_TRANSAKSI,
                                                  @Field("ID_CABANG") int ID_CABANG,
                                                  @Field("ID_PELANGGAN") int ID_PELANGGAN,
                                                  @Field("TGL_TRANSAKSI") String TGL_TRANSAKSI,
                                                  @Field("SUBTOTAL") double SUBTOTAL,
                                                  @Field("DISKON") double DISKON,
                                                  @Field("GRANDTOTAL") double GRANDTOTAL,
                                                  @Field("STATUS_TRANSAKSI") String STATUS_TRANSAKSI,
                                                  @Field("JENIS_TRANSAKSI") String JENIS_TRANSAKSI);

    @DELETE("transaksiPenjualan/{ID_TRANSAKSI}")
    Call<PenjualanDAO> deleteDataTransaksiPenjualan (@Path("ID_TRANSAKSI") String ID_TRANSAKSI);

    //====================Montir On Duty=========================================================
    @GET("montirOnDuty")
    Call<List<MontirOnDutyDAO>> getDetailMontirOnDuty();


    @FormUrlEncoded
    @POST("montirOnDuty")
    Call<MontirOnDutyDAO> createDataMontirOnDuty(@Field("ID_PEGAWAI") int ID_PEGAWAI,
                                                 @Field("ID_KENDARAAN_PEL") int ID_KENDARAAN_PEL);

    @FormUrlEncoded
    @PUT("montirOnDuty/{ID_MONTIR_ONDUTY}")
    Call<MontirOnDutyDAO> editDataMontirOnDuty(@Path("ID_MONTIR_ONDUTY") int ID_MONTIR_ONDUTY,
                                               @Field("ID_PEGAWAI") int ID_PEGAWAI,
                                               @Field("ID_KENDARAAN_PEL") int ID_KENDARAAN_PEL);

    @FormUrlEncoded
    @GET("montirOnDuty/{ID_MONTIR_ONDUTY}")
    Call<MontirOnDutyDAO> readDataMontirOnDuty(@Path("ID_MONTIR_ONDUTY") int ID_MONTIR_ONDUTY,
                                               @Field("ID_PEGAWAI") int ID_PEGAWAI,
                                               @Field("ID_KENDARAAN_PEL") int ID_KENDARAAN_PEL);

    @DELETE("montirOnDuty/{ID_MONTIR_ONDUTY}")
    Call<MontirOnDutyDAO> deleteDataMontirOnDuty(@Path("ID_MONTIR_ONDUTY") int ID_MONTIR_ONDUTY);


    //====================Pegawai=========================================================
    @GET("pegawai")
    Call<List<PegawaiDAO>> getPegawai();

    //=======================Sorting==============================================================
    @GET("sortJumlahAsc")
    Call<JSONResponse> sortingJumlahAsc();

    @GET("sortJumlahDesc")
    Call<JSONResponse> sortingJumlahDesc();

    @GET("sortHargaAsc")
    Call<JSONResponse> sortingHargaAsc();

    @GET("sortHargaDesc")
    Call<JSONResponse> sortingHargaDesc();



}
