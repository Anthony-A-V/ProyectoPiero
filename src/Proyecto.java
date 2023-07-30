import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.util.Random;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Proyecto extends JApplet implements ActionListener, ItemListener {
    // Declaracion de variables
    // Variables de GUI
    JLabel lblCajero, lblNumeroCuenta, lblClave, lblMensajeInicio, lblMensajeMenu, lblCuenta,
            lblCliente, lblFecha, lblHora, lblMensajeRetiro, lblTituloRetiro,
            lblTituloConsultaSaldo, lblReporteConsultaSaldo, lblTituloConsultaMovimientos,
            lblReporteConsultaMovimientos, lblTituloConsultaDatosCuenta, lblReporteConsultaDatosCuenta,
            lblTituloEstadistica, lblReporteConsultaEstadistica, lblTituloServicios, lblReporteServicios,
            lblNumeroServicio, lblPagoServicio, lblCuentaDestino, lblImporteTransferir, lblReporteTransferencia,
            lblTituloTransferencias, lblTituloClave, lblMensajeClave, lblNuevaClave, lblConfirmacionClave,
            lblMensajeClaveError;
    JTextField txtCuenta, txtCantidadRetiro, txtNumeroServicio, txtImporteServicio, txtCuentaDestino,
            txtImporteTransferir, txtNuevaClave, txtConfirmacionClave;
    JPasswordField txtClave;
    JButton btnIngresar, btnRetiro, btnConsultas, btnPagoServicios, btnTransferencias, btnCambioClave, btnSoles,
            btnDolares, btnRetirarDinero, btnMenu, btnConsultaSaldo, btnConsultaMovimientos,
            btnConsultaDatosCuenta, btnConsultaEstadistica, btnPagarServicio, btnTransferir, btnCambiarClave,
            btnCerrarSesion;
    JPanel panelLogin, panelMenu, panelDatosCliente, panelOpcionRetiro, panelRetiroDinero, panelOpcionConsulta,
            panelConsultaSaldo, panelConsultaMovimientos, panelConsultaDatosCuenta, panelConsultaEstadistica,
            panelOpcionServicios, panelOpcionTransferencias, panelOpcionClave;
    JComboBox cbxServicios;
    // Variables del sistema
    Cuenta[] cuentas;
    boolean cuentaCorrecta;
    boolean isDolares;
    int numeroRetiros;
    int indiceCuenta = -1;
    double montoLimiteDiario;
    double saldoTotalRetirado;
    double saldoInicial;
    String servicioSeleccionado;

    // Crea la interfaz grafica de usuario
    public void init() {
        setLayout(null);
        cargarCuentas();
        cargarGUILogin();
        cargarGUIDatosCliente();
        cargarGUIMenu();
        cargarGUIOpcionRetiro();
        cargarGUIRetiroDinero();
        cargarGUIOpcionConsultas();
        cargarGUIConsultaSaldo();
        cargarGUIConsultaMovimientos();
        cargarGUIConsultaDatosCuenta();
        cargarGUIConsultaEstadistica();
        cargarGUIOpcionServicios();
        cargarGUIOpcionTransferencias();
        cargarGUIOpcionClave();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnIngresar) {
            cuentaCorrecta = verificarCuenta();

            if (cuentaCorrecta) {
                // Inicializacion de varibles al iniciar sesion con una cuenta
                isDolares = false;
                numeroRetiros = 0;
                montoLimiteDiario = 1500;
                saldoTotalRetirado = 0;
                saldoInicial = cuentas[indiceCuenta].getSaldo();
                servicioSeleccionado = "";
                // Mostrar mensaje de inicio de sesion exitoso
                lblMensajeMenu.setText("Inicio de sesion exitoso! \nSeleccione una opcion:");
                lblMensajeMenu.setVisible(true);
                // Mostrar opciones
                panelLogin.setVisible(false);
                panelDatosCliente.setVisible(true);
                mostrarMenu();
                mostrarDatosCliente();
            } else {
                // Mostrar mensaje de validacion
                lblMensajeInicio.setBounds(350, 50, 300, 30);
                lblMensajeInicio.setText("Cuenta o clave incorrecta. Intente nuevamente.");
                lblMensajeInicio.setVisible(true);
            }
        }

        if (e.getSource() == btnRetiro) {
            // Mostrar el panel de retiro solo si se hace clic en el boton "Retiro de
            // dinero"
            panelMenu.setVisible(false);
            panelOpcionRetiro.setVisible(true);

        }

        if (e.getSource() == btnSoles || e.getSource() == btnDolares) {
            // Mostrar el panel para ingresar la cantidad de retiro en soles
            isDolares = false;
            panelMenu.setVisible(false);
            panelOpcionRetiro.setVisible(false);
            panelRetiroDinero.setVisible(true);
            txtCantidadRetiro.setText("");
            txtCantidadRetiro.setVisible(true);
            btnRetirarDinero.setVisible(true);
            lblMensajeRetiro.setVisible(false);

            if (e.getSource() == btnSoles) {
                isDolares = false;
                lblTituloRetiro.setText("RETIRO DE DINERO EN SOLES");

            } else {
                isDolares = true;
                lblTituloRetiro.setText("RETIRO DE DINERO EN DOLARES");
            }
        }

        if (e.getSource() == btnRetirarDinero) {
            panelMenu.setVisible(false);
            lblMensajeRetiro.setForeground(Color.BLACK);
            lblMensajeRetiro.setVisible(true);
            retirarDinero();
        }

        if (e.getSource() == btnConsultas) {
            panelMenu.setVisible(false);
            panelOpcionConsulta.setVisible(true);
        }

        if (e.getSource() == btnMenu) {
            panelOpcionRetiro.setVisible(false);
            panelRetiroDinero.setVisible(false);
            panelOpcionConsulta.setVisible(false);
            panelConsultaSaldo.setVisible(false);
            panelConsultaMovimientos.setVisible(false);
            panelConsultaDatosCuenta.setVisible(false);
            panelConsultaEstadistica.setVisible(false);
            panelOpcionServicios.setVisible(false);
            panelOpcionTransferencias.setVisible(false);
            panelOpcionClave.setVisible(false);
            panelMenu.setVisible(true);
        }

        if (e.getSource() == btnCerrarSesion) {
            indiceCuenta = -1;
            panelOpcionRetiro.setVisible(false);
            panelRetiroDinero.setVisible(false);
            panelOpcionConsulta.setVisible(false);
            panelConsultaSaldo.setVisible(false);
            panelConsultaMovimientos.setVisible(false);
            panelConsultaDatosCuenta.setVisible(false);
            panelConsultaEstadistica.setVisible(false);
            panelOpcionServicios.setVisible(false);
            panelOpcionTransferencias.setVisible(false);
            panelOpcionClave.setVisible(false);
            panelMenu.setVisible(false);
            panelLogin.setVisible(false);
            panelDatosCliente.setVisible(false);
            panelLogin.setVisible(true);
            txtCuenta.setText("");
            txtClave.setText("");
        }

        if (e.getSource() == btnConsultaSaldo) {
            panelOpcionConsulta.setVisible(false);
            panelConsultaSaldo.setVisible(true);
            mostrarReporteSaldo();
        }

        if (e.getSource() == btnConsultaMovimientos) {
            panelOpcionConsulta.setVisible(false);
            panelConsultaMovimientos.setVisible(true);
            mostrarReporteMovimientos();
        }

        if (e.getSource() == btnConsultaDatosCuenta) {
            panelOpcionConsulta.setVisible(false);
            panelConsultaDatosCuenta.setVisible(true);
            mostrarReporteDatos();
        }

        if (e.getSource() == btnConsultaEstadistica) {
            panelOpcionConsulta.setVisible(false);
            panelConsultaEstadistica.setVisible(true);
            mostrarReporteEstadistica();
        }

        if (e.getSource() == btnPagoServicios) {
            txtImporteServicio.setText("");
            txtNumeroServicio.setText("");
            cbxServicios.setVisible(true);
            lblNumeroServicio.setVisible(true);
            txtNumeroServicio.setVisible(true);
            lblPagoServicio.setVisible(true);
            txtImporteServicio.setVisible(true);
            btnPagarServicio.setVisible(true);
            lblReporteServicios.setVisible(false);
            servicioSeleccionado = "Luz";
            panelMenu.setVisible(false);
            panelOpcionServicios.setVisible(true);
        }

        if (e.getSource() == btnPagarServicio) {
            cbxServicios.setVisible(false);
            lblNumeroServicio.setVisible(false);
            txtNumeroServicio.setVisible(false);
            lblPagoServicio.setVisible(false);
            txtImporteServicio.setVisible(false);
            btnPagarServicio.setVisible(false);
            lblReporteServicios.setVisible(true);
            mostrarPagoServicio();
        }

        if (e.getSource() == btnTransferencias) {
            txtCuentaDestino.setText("");
            txtImporteTransferir.setText("");
            panelMenu.setVisible(false);
            lblCuentaDestino.setVisible(true);
            txtCuentaDestino.setVisible(true);
            lblImporteTransferir.setVisible(true);
            txtImporteTransferir.setVisible(true);
            lblReporteTransferencia.setVisible(false);
            btnTransferir.setVisible(true);
            panelOpcionTransferencias.setVisible(true);
        }

        if (e.getSource() == btnTransferir) {
            lblCuentaDestino.setVisible(false);
            txtCuentaDestino.setVisible(false);
            lblImporteTransferir.setVisible(false);
            txtImporteTransferir.setVisible(false);
            lblReporteTransferencia.setVisible(true);
            btnTransferir.setVisible(false);
            mostrarTransferencia();
        }

        if (e.getSource() == btnCambioClave) {
            txtNuevaClave.setText("");
            txtConfirmacionClave.setText("");
            panelMenu.setVisible(false);
            lblNuevaClave.setVisible(true);
            txtNuevaClave.setVisible(true);
            lblConfirmacionClave.setVisible(true);
            txtConfirmacionClave.setVisible(true);
            lblMensajeClave.setVisible(false);
            lblMensajeClaveError.setVisible(false);
            btnCambiarClave.setVisible(true);
            panelOpcionClave.setVisible(true);
        }

        if (e.getSource() == btnCambiarClave) {

            if (cambioClave() == true) {
                lblNuevaClave.setVisible(false);
                txtNuevaClave.setVisible(false);
                lblConfirmacionClave.setVisible(false);
                txtConfirmacionClave.setVisible(false);
                lblMensajeClave.setVisible(true);
                lblMensajeClaveError.setVisible(false);
                btnCambiarClave.setVisible(false);
            } else {
                lblMensajeClaveError.setVisible(true);
            }
        }
    }

    // Metodo usado para escuchar cuando el item de un ComboBox es seleccionado
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == cbxServicios) {
            servicioSeleccionado = (String) cbxServicios.getSelectedItem();
        }

        if (servicioSeleccionado == "Telefono") {
            lblNumeroServicio.setText("Numero de Telefono");
        } else {
            lblNumeroServicio.setText("Numero de Suministro");
        }
    }

    // Genera un tipo de cambio aleatorio entre S/. 2.90 y S/. 3.50
    private double tipoCambioAleatorio() {
        Random random = new Random();
        return 2.90 + (random.nextDouble() * (3.50 - 2.90));
    }

    // Metodo para inicializar Cuentas Bancarias
    private void cargarCuentas() {
        // Inicializar cuentas con saldos aleatorios
        Random random = new Random();
        cuentas = new Cuenta[] {
                new Cuenta("Maximiliano Habsburgo Lorena", "1111", "1111", (random.nextDouble() * (3000 - 500) + 500),
                        "Av. Los Pinos 123, Miraflores", "Lima", "985452652"),
                new Cuenta("Isabel Castilla Leon", "2222", "2222", (random.nextDouble() * (3000 - 500) + 500),
                        "Jr. Las Rosas 456, Surco", "Lima", "985265402"),
                new Cuenta("Cesar Augusto Roma Bizancio", "3333", "3333", (random.nextDouble() * (3000 - 500) + 500),
                        "Calle Los Cerezos 789, San Isidro", "Lima", "958025498"),
                new Cuenta("Felipe Carlos Habsburgo Borgoa", "4444", "4444",
                        (random.nextDouble() * (3000 - 500) + 500),
                        "Av. Las Palmeras 1010", "Lima", "923902784"),
                new Cuenta("Fernando Aragon Trastamara", "5555", "5555", (random.nextDouble() * (3000 - 500) + 500),
                        "Pasaje Las Orqu\u00EDdeas 222", "Lima", "985022015")
        };
    }

    private String convertirADosDecimales(Double numero) {
        DecimalFormat formatoDecimal = new DecimalFormat("#.00");
        String numeroFormateado = formatoDecimal.format(numero);
        return numeroFormateado;
    }

    // Metodo para inicializar el login
    private void cargarGUILogin() {
        panelLogin = new JPanel();
        panelLogin.setLayout(null);

        lblCajero = new JLabel("INTERBANK");
        lblCajero.setBounds(460, 0, 150, 30);
        panelLogin.add(lblCajero);

        lblNumeroCuenta = new JLabel("Numero de cuenta: ");
        lblNumeroCuenta.setBounds(350, 100, 150, 30);
        panelLogin.add(lblNumeroCuenta);

        txtCuenta = new JTextField("1111");
        txtCuenta.setBounds(550, 100, 100, 30);
        panelLogin.add(txtCuenta);

        lblClave = new JLabel("Clave secreta: ");
        lblClave.setBounds(350, 150, 150, 30);
        panelLogin.add(lblClave);

        txtClave = new JPasswordField("1111");
        txtClave.setBounds(550, 150, 100, 30);
        panelLogin.add(txtClave);

        btnIngresar = new JButton("Ingresar");
        btnIngresar.setBounds(450, 200, 100, 30);
        btnIngresar.addActionListener(this);
        panelLogin.add(btnIngresar);

        lblMensajeInicio = new JLabel();
        lblMensajeInicio.setForeground(Color.RED);
        lblMensajeInicio.setVisible(false);
        lblMensajeInicio.setBounds(320, 150, 300, 30);
        panelLogin.add(lblMensajeInicio);

        panelLogin.setVisible(true);
        panelLogin.setBounds(0, 0, 1024, 720);
        add(panelLogin);
    }

    // Metodo para inicializar el Menu de opciones
    private void cargarGUIMenu() {
        panelMenu = new JPanel();
        panelMenu.setLayout(null);

        btnRetiro = new JButton("Retiro de dinero");
        btnRetiro.setBounds(400, 200, 200, 30);
        btnRetiro.setVisible(false);
        btnRetiro.addActionListener(this);
        panelMenu.add(btnRetiro);

        btnConsultas = new JButton("Consultas diversas");
        btnConsultas.setBounds(400, 250, 200, 30);
        btnConsultas.setVisible(false);
        btnConsultas.addActionListener(this);
        panelMenu.add(btnConsultas);

        btnPagoServicios = new JButton("Pago de servicios");
        btnPagoServicios.setBounds(400, 300, 200, 30);
        btnPagoServicios.setVisible(false);
        btnPagoServicios.addActionListener(this);
        panelMenu.add(btnPagoServicios);

        btnTransferencias = new JButton("Transferencia entre cuentas");
        btnTransferencias.setBounds(400, 350, 200, 30);
        btnTransferencias.setVisible(false);
        btnTransferencias.addActionListener(this);
        panelMenu.add(btnTransferencias);

        btnCambioClave = new JButton("Cambio de clave secreta");
        btnCambioClave.setBounds(400, 400, 200, 30);
        btnCambioClave.setVisible(false);
        btnCambioClave.addActionListener(this);
        panelMenu.add(btnCambioClave);

        lblMensajeMenu = new JLabel();
        lblMensajeMenu.setForeground(Color.BLACK);
        lblMensajeMenu.setVisible(false);
        lblMensajeMenu.setBounds(370, 150, 300, 30);
        panelMenu.add(lblMensajeMenu);

        panelMenu.setVisible(false);
        panelMenu.setBounds(0, 0, 1024, 720);
        panelDatosCliente.add(panelMenu);
    }

    // Metodo para inicilializar Datos del cliente
    private void cargarGUIDatosCliente() {

        panelDatosCliente = new JPanel();
        panelDatosCliente.setLayout(null);

        lblCuenta = new JLabel();
        lblCuenta.setBounds(200, 50, 100, 30);
        lblCuenta.setVisible(false);
        panelDatosCliente.add(lblCuenta);

        lblCliente = new JLabel();
        lblCliente.setBounds(200, 100, 300, 30);
        lblCliente.setVisible(false);
        panelDatosCliente.add(lblCliente);

        lblFecha = new JLabel();
        lblFecha.setBounds(640, 50, 200, 30);
        lblFecha.setVisible(false);
        panelDatosCliente.add(lblFecha);

        lblHora = new JLabel();
        lblHora.setBounds(640, 100, 100, 30);
        lblHora.setVisible(false);
        panelDatosCliente.add(lblHora);

        btnCerrarSesion = new JButton("Cerrar Sesion");
        btnCerrarSesion.setForeground(Color.BLACK);
        btnCerrarSesion.setVisible(false);
        btnCerrarSesion.setBounds(830, 50, 120, 30);
        btnCerrarSesion.addActionListener(this);
        panelDatosCliente.add(btnCerrarSesion);

        btnMenu = new JButton("Menu");
        btnMenu.setForeground(Color.BLACK);
        btnMenu.setVisible(false);
        btnMenu.setBounds(830, 100, 120, 30);
        btnMenu.addActionListener(this);
        panelDatosCliente.add(btnMenu);

        panelDatosCliente.setVisible(true);
        panelDatosCliente.setBounds(0, 0, 1024, 720);
        add(panelDatosCliente);
    }

    private void cargarGUIOpcionRetiro() {
        // Crea el panel con las opciones de retiro en soles y dolares
        panelOpcionRetiro = new JPanel();
        panelOpcionRetiro.setLayout(null);

        btnSoles = new JButton("Retiro en soles");
        btnSoles.setBounds(420, 200, 150, 30);
        btnSoles.addActionListener(this);
        panelOpcionRetiro.add(btnSoles);

        btnDolares = new JButton("Retiro en dolares");
        btnDolares.setBounds(420, 250, 150, 30);
        btnDolares.addActionListener(this);
        panelOpcionRetiro.add(btnDolares);

        panelOpcionRetiro.setBounds(0, 0, 1000, 1000);
        panelOpcionRetiro.setVisible(false);
        panelDatosCliente.add(panelOpcionRetiro);
    }

    private void cargarGUIRetiroDinero() {
        // Crea el panel para ingresar la cantidad de retiro
        panelRetiroDinero = new JPanel();
        panelRetiroDinero.setLayout(null);

        txtCantidadRetiro = new JTextField();
        txtCantidadRetiro.setBounds(450, 200, 100, 30);
        panelRetiroDinero.add(txtCantidadRetiro);

        btnRetirarDinero = new JButton("Retirar");
        btnRetirarDinero.setBounds(450, 250, 100, 30);
        btnRetirarDinero.addActionListener(this);
        panelRetiroDinero.add(btnRetirarDinero);

        lblTituloRetiro = new JLabel();
        lblTituloRetiro.setForeground(Color.BLACK);
        lblTituloRetiro.setVisible(true);
        lblTituloRetiro.setBounds(420, 150, 300, 30);
        panelRetiroDinero.add(lblTituloRetiro);

        lblMensajeRetiro = new JLabel();
        lblMensajeRetiro.setForeground(Color.BLACK);
        lblMensajeRetiro.setVisible(false);
        lblMensajeRetiro.setBounds(370, 200, 300, 200);
        panelRetiroDinero.add(lblMensajeRetiro);

        panelRetiroDinero.setBounds(0, 0, 1000, 1000);
        panelRetiroDinero.setVisible(false);
        panelDatosCliente.add(panelRetiroDinero);
    }

    private void cargarGUIOpcionConsultas() {
        panelOpcionConsulta = new JPanel();
        panelOpcionConsulta.setLayout(null);

        btnConsultaSaldo = new JButton("Consulta de saldo");
        btnConsultaSaldo.setForeground(Color.BLACK);
        btnConsultaSaldo.setVisible(true);
        btnConsultaSaldo.setBounds(350, 200, 300, 30);
        btnConsultaSaldo.addActionListener(this);
        panelOpcionConsulta.add(btnConsultaSaldo);

        btnConsultaMovimientos = new JButton("Consulta de movimientos");
        btnConsultaMovimientos.setForeground(Color.BLACK);
        btnConsultaMovimientos.setVisible(true);
        btnConsultaMovimientos.setBounds(350, 250, 300, 30);
        btnConsultaMovimientos.addActionListener(this);
        panelOpcionConsulta.add(btnConsultaMovimientos);

        btnConsultaDatosCuenta = new JButton("Consulta de datos de la cuenta");
        btnConsultaDatosCuenta.setForeground(Color.BLACK);
        btnConsultaDatosCuenta.setVisible(true);
        btnConsultaDatosCuenta.setBounds(350, 300, 300, 30);
        btnConsultaDatosCuenta.addActionListener(this);
        panelOpcionConsulta.add(btnConsultaDatosCuenta);

        btnConsultaEstadistica = new JButton("Consulta estadistica");
        btnConsultaEstadistica.setForeground(Color.BLACK);
        btnConsultaEstadistica.setVisible(true);
        btnConsultaEstadistica.setBounds(350, 350, 300, 30);
        btnConsultaEstadistica.addActionListener(this);
        panelOpcionConsulta.add(btnConsultaEstadistica);

        panelOpcionConsulta.setBounds(0, 0, 1000, 1000);
        panelOpcionConsulta.setVisible(false);
        panelDatosCliente.add(panelOpcionConsulta);
    }

    private void cargarGUIConsultaSaldo() {
        panelConsultaSaldo = new JPanel();
        panelConsultaSaldo.setLayout(null);

        lblTituloConsultaSaldo = new JLabel("CONSULTA DE SALDO");
        lblTituloConsultaSaldo.setForeground(Color.BLACK);
        lblTituloConsultaSaldo.setVisible(true);
        lblTituloConsultaSaldo.setBounds(430, 150, 300, 30);
        panelConsultaSaldo.add(lblTituloConsultaSaldo);

        lblReporteConsultaSaldo = new JLabel();
        lblReporteConsultaSaldo.setForeground(Color.BLACK);
        lblReporteConsultaSaldo.setVisible(true);
        lblReporteConsultaSaldo.setBounds(400, 150, 300, 200);
        panelConsultaSaldo.add(lblReporteConsultaSaldo);

        panelConsultaSaldo.setBounds(0, 0, 1000, 1000);
        panelConsultaSaldo.setVisible(false);
        panelDatosCliente.add(panelConsultaSaldo);
    }

    private void cargarGUIConsultaMovimientos() {
        panelConsultaMovimientos = new JPanel();
        panelConsultaMovimientos.setLayout(null);

        lblTituloConsultaMovimientos = new JLabel("CONSULTA DE MOVIMIENTOS");
        lblTituloConsultaMovimientos.setForeground(Color.BLACK);
        lblTituloConsultaMovimientos.setVisible(true);
        lblTituloConsultaMovimientos.setBounds(420, 150, 300, 30);
        panelConsultaMovimientos.add(lblTituloConsultaMovimientos);

        lblReporteConsultaMovimientos = new JLabel();
        lblReporteConsultaMovimientos.setForeground(Color.BLACK);
        lblReporteConsultaMovimientos.setVisible(true);
        lblReporteConsultaMovimientos.setBounds(400, 150, 300, 200);
        panelConsultaMovimientos.add(lblReporteConsultaMovimientos);

        panelConsultaMovimientos.setBounds(0, 0, 1000, 1000);
        panelConsultaMovimientos.setVisible(false);
        panelDatosCliente.add(panelConsultaMovimientos);
    }

    private void cargarGUIConsultaDatosCuenta() {
        panelConsultaDatosCuenta = new JPanel();
        panelConsultaDatosCuenta.setLayout(null);

        lblTituloConsultaDatosCuenta = new JLabel("CONSULTA DE DATOS DE LA CUENTA");
        lblTituloConsultaDatosCuenta.setForeground(Color.BLACK);
        lblTituloConsultaDatosCuenta.setVisible(true);
        lblTituloConsultaDatosCuenta.setBounds(420, 150, 300, 30);
        panelConsultaDatosCuenta.add(lblTituloConsultaDatosCuenta);

        lblReporteConsultaDatosCuenta = new JLabel();
        lblReporteConsultaDatosCuenta.setForeground(Color.BLACK);
        lblReporteConsultaDatosCuenta.setVisible(true);
        lblReporteConsultaDatosCuenta.setBounds(400, 150, 300, 200);
        panelConsultaDatosCuenta.add(lblReporteConsultaDatosCuenta);

        panelConsultaDatosCuenta.setBounds(0, 0, 1000, 1000);
        panelConsultaDatosCuenta.setVisible(false);
        panelDatosCliente.add(panelConsultaDatosCuenta);
    }

    private void cargarGUIConsultaEstadistica() {
        panelConsultaEstadistica = new JPanel();
        panelConsultaEstadistica.setLayout(null);

        lblTituloEstadistica = new JLabel("CONSULTA ESTADISTICA");
        lblTituloEstadistica.setForeground(Color.BLACK);
        lblTituloEstadistica.setVisible(true);
        lblTituloEstadistica.setBounds(420, 150, 300, 30);
        panelConsultaEstadistica.add(lblTituloEstadistica);

        lblReporteConsultaEstadistica = new JLabel();
        lblReporteConsultaEstadistica.setForeground(Color.BLACK);
        lblReporteConsultaEstadistica.setVisible(true);
        lblReporteConsultaEstadistica.setBounds(420, 150, 300, 200);
        panelConsultaEstadistica.add(lblReporteConsultaEstadistica);

        panelConsultaEstadistica.setBounds(0, 0, 1000, 1000);
        panelConsultaEstadistica.setVisible(false);
        panelDatosCliente.add(panelConsultaEstadistica);
    }

    private void cargarGUIOpcionServicios() {

        panelOpcionServicios = new JPanel();
        panelOpcionServicios.setLayout(null);

        lblTituloServicios = new JLabel("PAGO DE SERVICIOS");
        lblTituloServicios.setForeground(Color.BLACK);
        lblTituloServicios.setVisible(true);
        lblTituloServicios.setBounds(430, 150, 300, 30);
        panelOpcionServicios.add(lblTituloServicios);

        cbxServicios = new JComboBox();
        cbxServicios.addItem("Luz");
        cbxServicios.addItem("Agua");
        cbxServicios.addItem("Telefono");
        cbxServicios.setBounds(420, 200, 200, 30);
        cbxServicios.addItemListener(this);
        panelOpcionServicios.add(cbxServicios);

        lblNumeroServicio = new JLabel("Numero de Suministro");
        lblNumeroServicio.setForeground(Color.BLACK);
        lblNumeroServicio.setVisible(true);
        lblNumeroServicio.setBounds(455, 250, 200, 30);
        panelOpcionServicios.add(lblNumeroServicio);

        txtNumeroServicio = new JTextField();
        txtNumeroServicio.setBounds(465, 300, 100, 30);
        panelOpcionServicios.add(txtNumeroServicio);

        lblPagoServicio = new JLabel("Importe a pagar");
        lblPagoServicio.setForeground(Color.BLACK);
        lblPagoServicio.setVisible(true);
        lblPagoServicio.setBounds(470, 350, 200, 30);
        panelOpcionServicios.add(lblPagoServicio);

        txtImporteServicio = new JTextField();
        txtImporteServicio.setBounds(465, 400, 100, 30);
        panelOpcionServicios.add(txtImporteServicio);

        btnPagarServicio = new JButton("Pagar servicio");
        btnPagarServicio.setBounds(450, 450, 130, 30);
        btnPagarServicio.setVisible(true);
        btnPagarServicio.addActionListener(this);
        panelOpcionServicios.add(btnPagarServicio);

        lblReporteServicios = new JLabel();
        lblReporteServicios.setForeground(Color.BLACK);
        lblReporteServicios.setVisible(true);
        lblReporteServicios.setBounds(420, 150, 300, 200);
        panelOpcionServicios.add(lblReporteServicios);

        panelOpcionServicios.setBounds(0, 0, 1000, 1000);
        panelOpcionServicios.setVisible(false);
        panelDatosCliente.add(panelOpcionServicios);
    }

    private void cargarGUIOpcionTransferencias() {
        panelOpcionTransferencias = new JPanel();
        panelOpcionTransferencias.setLayout(null);

        lblTituloTransferencias = new JLabel("TRANSFERENCIA DE DINERO");
        lblTituloTransferencias.setForeground(Color.BLACK);
        lblTituloTransferencias.setVisible(true);
        lblTituloTransferencias.setBounds(430, 150, 300, 30);
        panelOpcionTransferencias.add(lblTituloTransferencias);

        lblCuentaDestino = new JLabel("Cuenta de destino");
        lblCuentaDestino.setForeground(Color.BLACK);
        lblCuentaDestino.setVisible(true);
        lblCuentaDestino.setBounds(465, 250, 200, 30);
        panelOpcionTransferencias.add(lblCuentaDestino);

        txtCuentaDestino = new JTextField();
        txtCuentaDestino.setBounds(465, 300, 100, 30);
        panelOpcionTransferencias.add(txtCuentaDestino);

        lblImporteTransferir = new JLabel("Importe a transferir");
        lblImporteTransferir.setForeground(Color.BLACK);
        lblImporteTransferir.setVisible(true);
        lblImporteTransferir.setBounds(460, 350, 200, 30);
        panelOpcionTransferencias.add(lblImporteTransferir);

        txtImporteTransferir = new JTextField();
        txtImporteTransferir.setBounds(465, 400, 100, 30);
        panelOpcionTransferencias.add(txtImporteTransferir);

        btnTransferir = new JButton("Transferir");
        btnTransferir.setBounds(450, 450, 130, 30);
        btnTransferir.setVisible(true);
        btnTransferir.addActionListener(this);
        panelOpcionTransferencias.add(btnTransferir);

        lblReporteTransferencia = new JLabel();
        lblReporteTransferencia.setForeground(Color.BLACK);
        lblReporteTransferencia.setVisible(true);
        lblReporteTransferencia.setBounds(420, 150, 300, 200);
        panelOpcionTransferencias.add(lblReporteTransferencia);

        panelOpcionTransferencias.setBounds(0, 0, 1000, 1000);
        panelOpcionTransferencias.setVisible(false);
        panelDatosCliente.add(panelOpcionTransferencias);
    }

    private void cargarGUIOpcionClave() {
        panelOpcionClave = new JPanel();
        panelOpcionClave.setLayout(null);

        lblTituloClave = new JLabel("CAMBIO DE CLAVE");
        lblTituloClave.setForeground(Color.BLACK);
        lblTituloClave.setVisible(true);
        lblTituloClave.setBounds(465, 150, 300, 30);
        panelOpcionClave.add(lblTituloClave);

        lblNuevaClave = new JLabel("Nueva Clave");
        lblNuevaClave.setForeground(Color.BLACK);
        lblNuevaClave.setVisible(true);
        lblNuevaClave.setBounds(480, 250, 200, 30);
        panelOpcionClave.add(lblNuevaClave);

        txtNuevaClave = new JTextField();
        txtNuevaClave.setBounds(465, 300, 100, 30);
        panelOpcionClave.add(txtNuevaClave);

        lblConfirmacionClave = new JLabel("Confirmacion de Clave");
        lblConfirmacionClave.setForeground(Color.BLACK);
        lblConfirmacionClave.setVisible(true);
        lblConfirmacionClave.setBounds(455, 350, 200, 30);
        panelOpcionClave.add(lblConfirmacionClave);

        txtConfirmacionClave = new JTextField();
        txtConfirmacionClave.setBounds(465, 400, 100, 30);
        panelOpcionClave.add(txtConfirmacionClave);

        btnCambiarClave = new JButton("Cambiar clave");
        btnCambiarClave.setBounds(450, 450, 130, 30);
        btnCambiarClave.setVisible(true);
        btnCambiarClave.addActionListener(this);
        panelOpcionClave.add(btnCambiarClave);

        lblMensajeClave = new JLabel();
        lblMensajeClave.setForeground(Color.BLACK);
        lblMensajeClave.setVisible(false);
        lblMensajeClave.setBounds(440, 150, 300, 200);
        panelOpcionClave.add(lblMensajeClave);

        lblMensajeClaveError = new JLabel();
        lblMensajeClaveError.setForeground(Color.RED);
        lblMensajeClaveError.setVisible(false);
        lblMensajeClaveError.setBounds(420, 500, 300, 30);
        panelOpcionClave.add(lblMensajeClaveError);

        panelOpcionClave.setBounds(0, 0, 1000, 1000);
        panelOpcionClave.setVisible(false);
        panelDatosCliente.add(panelOpcionClave);
    }

    private void mostrarMenu() {
        btnRetiro.setVisible(true);
        btnConsultas.setVisible(true);
        btnPagoServicios.setVisible(true);
        btnTransferencias.setVisible(true);
        btnCambioClave.setVisible(true);
        lblMensajeMenu.setVisible(true);
        panelMenu.setVisible(true);
    }

    // Metodo para Verificar si la cuenta y la clave son correctas
    private boolean verificarCuenta() {
        String cuenta = txtCuenta.getText();
        String clave = new String(txtClave.getPassword());
        boolean isCorrecto = false;

        for (int i = 0; i < cuentas.length; i++) {
            if (cuentas[i].getNumeroCuenta().equals(cuenta) && cuentas[i].getClave().equals(clave)) {
                isCorrecto = true;
                indiceCuenta = i;
                break;
            }
        }

        return isCorrecto;
    }

    private void mostrarDatosCliente() {
        Date fechaYHoraActual = new Date();

        lblCuenta.setText("CUENTA: " + cuentas[indiceCuenta].getNumeroCuenta());
        lblCuenta.setVisible(true);

        lblCliente.setText("CLIENTE: " + cuentas[indiceCuenta].getNombreCliente());
        lblCliente.setVisible(true);

        lblFecha.setText("FECHA: " + formatearFecha(fechaYHoraActual));
        lblFecha.setVisible(true);

        lblHora.setText("HORA: " + formatearHora(fechaYHoraActual));
        lblHora.setVisible(true);

        btnMenu.setVisible(true);
        btnCerrarSesion.setVisible(true);
    }

    private void mostrarReporteSaldo() {
        if (numeroRetiros == 0) {
            lblReporteConsultaSaldo.setText("<html>" +
                    "SALDO DISPONIBLE ANTERIOR: S/. " + convertirADosDecimales(saldoInicial) + "<br>" +
                    "IMPORTE RETIRADO: S/. 0.00<br>" +
                    "SALDO DISPONIBLE ACTUAL: S/. " + convertirADosDecimales(cuentas[indiceCuenta].getSaldo())
                    + "</html>");
        } else {
            lblReporteConsultaSaldo.setText("<html>" +
                    "SALDO DISPONIBLE ANTERIOR: S/. " + convertirADosDecimales(saldoInicial) + "<br>" +
                    "IMPORTE RETIRADO: S/. " + convertirADosDecimales(saldoTotalRetirado) + "<br>" +
                    "SALDO DISPONIBLE ACTUAL: S/. " + convertirADosDecimales(cuentas[indiceCuenta].getSaldo())
                    + "</html>");
        }

    }

    private void mostrarReporteMovimientos() {

        // Si no hay movimientos registrados, no se muestra el reporte
        if (cuentas[indiceCuenta].getMovimientos().isEmpty()) {
            lblReporteConsultaMovimientos.setText("No hay movimientos registrados");
            return;
        }

        // Se usa la entidad de caracter "&nbsp" para dar tabulaciones (espacios) al
        // texto
        lblReporteConsultaMovimientos.setText("");
        lblReporteConsultaMovimientos
                .setText("<html> FECHA &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp" +
                        " HORA &nbsp&nbsp DESCRIPCION &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp IMPORTE <br>");

        String textoRegistros = "";
        for (Movimiento movimiento : cuentas[indiceCuenta].getMovimientos()) {

            // Si es comision banco
            if (movimiento.descripcion.equals("Retiro")) {
                textoRegistros = textoRegistros + movimiento.getFecha()
                        + " &nbsp&nbsp "
                        + movimiento.getHora() + " &nbsp&nbsp " + movimiento.getDescripcion() +
                        " &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp "
                        + "S/. " + convertirADosDecimales(movimiento.getImporte()) + " <br> ";
            } else if (movimiento.descripcion.equals("Comision Banco")) {
                textoRegistros = textoRegistros + movimiento.getFecha()
                        + " &nbsp&nbsp "
                        + movimiento.getHora() + " &nbsp&nbsp " + movimiento.getDescripcion() +
                        " &nbsp&nbsp S/. "
                        + 0.45 + " <br> ";
                // Si es pago de servicio
            } else if (movimiento.descripcion.equals("Luz")) {
                textoRegistros = textoRegistros + movimiento.getFecha()
                        + " &nbsp&nbsp "
                        + movimiento.getHora() + " &nbsp&nbsp " + movimiento.getDescripcion() +
                        " &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp "
                        + "S/. " + convertirADosDecimales(movimiento.getImporte()) + " <br> ";
                // Si es retiro
            } else if (movimiento.descripcion.equals("Agua")) {
                textoRegistros = textoRegistros + movimiento.getFecha()
                        + " &nbsp&nbsp "
                        + movimiento.getHora() + " &nbsp&nbsp " + movimiento.getDescripcion() +
                        " &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp "
                        + "S/. " + convertirADosDecimales(movimiento.getImporte()) + " <br> ";
            } else if (movimiento.descripcion.equals("Telefono")) {
                textoRegistros = textoRegistros + movimiento.getFecha()
                        + " &nbsp&nbsp "
                        + movimiento.getHora() + " &nbsp&nbsp " + movimiento.getDescripcion() +
                        " &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp "
                        + "S/. " + convertirADosDecimales(movimiento.getImporte()) + " <br> ";
            } else if (movimiento.descripcion.equals("Transferencia")) {
                textoRegistros = textoRegistros + movimiento.getFecha()
                        + " &nbsp&nbsp "
                        + movimiento.getHora() + " &nbsp&nbsp " + movimiento.getDescripcion() +
                        " &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp "
                        + "S/. " + convertirADosDecimales(movimiento.getImporte()) + " <br> ";
            }
        }

        lblReporteConsultaMovimientos.setText(lblReporteConsultaMovimientos.getText() + textoRegistros + "</html>");

    }

    private void mostrarReporteDatos() {

        // Datos de la cuenta
        lblReporteConsultaDatosCuenta.setText("<html>" +
                "CUENTA: " + cuentas[indiceCuenta].getNumeroCuenta() + "<br>" +
                "CLIENTE: " + cuentas[indiceCuenta].getNombreCliente() + "<br>" +
                "CALLE: " + cuentas[indiceCuenta].getCalle() + "<br>" +
                "CIUDAD: " + cuentas[indiceCuenta].getCiudad() + "<br>" +
                "CELULAR: " + cuentas[indiceCuenta].getCelular() + "</html>");
    }

    private void mostrarReporteEstadistica() {

        // Si no hay movimientos registrados, no se muestra el reporte
        if (numeroRetiros == 0) {
            lblReporteConsultaEstadistica.setText("No hay movimientos registrados");
            return;
        }

        lblReporteConsultaEstadistica.setText("<html>" +
                "NUMERO TOTAL DE RETIROS: " + numeroRetiros + "<br>" +
                "IMPORTE TOTAL RETIRADO: S/. " + convertirADosDecimales(saldoTotalRetirado) + "<br>" +
                "MAYOR RETIRO REALIZADO: S/. " + convertirADosDecimales(mayorRetiroRealizado()) + "<br>" +
                "MENOR RETIRO REALIZADO: S/. " + convertirADosDecimales(menorRetiroRealizado()) + "<br>" +
                "RETIRO PROMEDIO: S/. " + convertirADosDecimales(retiroPromedio()) + "</html>");
    }

    private void mostrarPagoServicio() {
        lblReporteServicios.setText("<html>" +
                "SERVICIO: " + servicioSeleccionado + "<br>" +
                "IMPORTE: S/. " + convertirADosDecimales(Double.parseDouble(txtImporteServicio.getText())) + "<br>" +
                "SALDO DISPONIBLE: S/. " + convertirADosDecimales(cuentas[indiceCuenta].getSaldo()) + "</html>");

        // Ingreso del movimiento de la cuenta
        Date fechaYHoraActual = new Date();
        Movimiento pagoServicio = new Movimiento();
        double importeServicio = Double.parseDouble(txtImporteServicio.getText());
        double saldoAnterior = cuentas[indiceCuenta].getSaldo();
        cuentas[indiceCuenta].setSaldo(saldoAnterior - importeServicio);

        pagoServicio.setFecha(formatearFecha(fechaYHoraActual));
        pagoServicio.setHora(formatearHora(fechaYHoraActual));
        pagoServicio.setDescripcion(servicioSeleccionado);
        pagoServicio.setImporte(importeServicio);
        pagoServicio.setTipoCambio(0);

        // Se agrega el movimiento a la lista de movimientos del usuario
        cuentas[indiceCuenta].agregarMovimiento(pagoServicio);
    }

    private void mostrarTransferencia() {
        lblReporteTransferencia.setText("<html>" +
                "CUENTA DE CARGO: " + cuentas[indiceCuenta].getNumeroCuenta() + "<br>" +
                "CUENTA DE DESTINO: " + txtCuentaDestino.getText() + "<br>" +
                "IMPORTE: S/. " + convertirADosDecimales(Double.parseDouble(txtImporteTransferir.getText())) + "<br>" +
                "SALDO DISPONIBLE: S/. " + convertirADosDecimales(cuentas[indiceCuenta].getSaldo()) + "</html>");

        // Ingreso del movimiento de la cuenta
        Date fechaYHoraActual = new Date();
        Movimiento transferencia = new Movimiento();
        double importeTransferencia = Double.parseDouble(txtImporteTransferir.getText());
        double saldoAnterior = cuentas[indiceCuenta].getSaldo();
        cuentas[indiceCuenta].setSaldo(saldoAnterior - importeTransferencia);
        transferencia.setFecha(formatearFecha(fechaYHoraActual));
        transferencia.setHora(formatearHora(fechaYHoraActual));
        transferencia.setDescripcion("Transferencia");
        transferencia.setImporte(importeTransferencia);
        transferencia.setTipoCambio(0);

        // Se agrega el movimiento a la lista de movimientos del usuario que hace el
        // cargo
        cuentas[indiceCuenta].agregarMovimiento(transferencia);

        // Se agrega la transferencia a la cuenta destino
        int indiceCuentaDestino = -1;
        for (int i = 0; i < cuentas.length; i++) {
            if (cuentas[i].getNumeroCuenta().equals(txtCuentaDestino.getText())) {
                indiceCuentaDestino = i;
                break;
            }
        }

        double saldoAnteriorDestino = cuentas[indiceCuenta].getSaldo();
        cuentas[indiceCuentaDestino].setSaldo(saldoAnteriorDestino + importeTransferencia);
        transferencia.setFecha(formatearFecha(fechaYHoraActual));
        transferencia.setHora(formatearHora(fechaYHoraActual));
        transferencia.setDescripcion("Transferencia");
        transferencia.setImporte(importeTransferencia);
        transferencia.setTipoCambio(0);

        // Se agrega el movimiento a la lista de movimientos del usuario destino
        cuentas[indiceCuentaDestino].agregarMovimiento(transferencia);
    }

    private boolean cambioClave() {
        String nuevaClave = txtNuevaClave.getText();
        String confirmacionClave = txtConfirmacionClave.getText();

        if (nuevaClave.equals(confirmacionClave)) {
            cuentas[indiceCuenta].setClave(nuevaClave);
            lblMensajeClave.setText("Su clave ha sido modificada");
            return true;
        } else {
            lblMensajeClaveError.setText("Las claves ingresadas no son iguales");
            return false;
        }
    }

    // Formatea la fecha a dd/MM/yyyy
    private String formatearFecha(Date fecha) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        String fechaFormateada = formatoFecha.format(fecha);
        return fechaFormateada;
    }

    // Formatea la hora a HH:mm
    private String formatearHora(Date fecha) {
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
        String horaFormateada = formatoHora.format(fecha);
        return horaFormateada;
    }

    private double mayorRetiroRealizado() {
        double mayorRetiro = 0;
        for (Movimiento movimiento : cuentas[indiceCuenta].getMovimientos()) {

            if (movimiento.descripcion == "Retiro") {
                if (mayorRetiro < movimiento.getImporte()) {
                    mayorRetiro = movimiento.getImporte();
                }
            }

        }
        return mayorRetiro;
    }

    private double menorRetiroRealizado() {
        double menorRetiro = Double.MAX_VALUE;
        for (Movimiento movimiento : cuentas[indiceCuenta].getMovimientos()) {

            if (movimiento.descripcion == "Retiro")
                if (movimiento.getImporte() < menorRetiro) {
                    menorRetiro = movimiento.getImporte();
                }
        }
        return menorRetiro;
    }

    private double retiroPromedio() {
        double sumaRetiros = 0;
        for (Movimiento movimiento : cuentas[indiceCuenta].getMovimientos()) {

            if (movimiento.getDescripcion() == "Retiro") {
                sumaRetiros = sumaRetiros + movimiento.getImporte();
            }
        }

        double promedio = sumaRetiros / numeroRetiros;
        return promedio;
    }

    private void retirarDinero() {
        double montoRetiro = 0;
        double montoEnDolares = 0;
        double tipoCambio = 0;

        if (!isDolares) {
            montoRetiro = Double.parseDouble(txtCantidadRetiro.getText());
            tipoCambio = 0;
        } else if (isDolares) {
            tipoCambio = tipoCambioAleatorio();
            montoEnDolares = Double.parseDouble(txtCantidadRetiro.getText());
            montoRetiro = montoEnDolares * tipoCambio;
        }

        // Para soles
        // Validar que el monto sea un multiplo de 50
        if (montoRetiro % 50 == 0 && !isDolares) {
            // Validar limite maximo de retiro por dia (S/. 1500)
            if (montoRetiro <= montoLimiteDiario) {
                // Verificar si hay saldo suficiente en la cuenta
                if (montoRetiro <= cuentas[indiceCuenta].getSaldo()) {

                    // Se ocultan las caja de texto y el boton
                    txtCantidadRetiro.setVisible(false);
                    btnRetirarDinero.setVisible(false);

                    // Realizar el retiro y actualiza el saldo
                    double saldoAnterior = cuentas[indiceCuenta].getSaldo();
                    double saldoNuevo = saldoAnterior - montoRetiro;
                    cuentas[indiceCuenta].setSaldo(saldoNuevo);

                    // Se suma el monto total retirado que se usara para el reporte
                    saldoTotalRetirado = saldoTotalRetirado + montoRetiro;

                    // Se reduce el monto de limite diario
                    montoLimiteDiario = montoLimiteDiario - montoRetiro;

                    // Ingreso del movimiento de la cuenta
                    Date fechaYHoraActual = new Date();
                    Movimiento movimiento = new Movimiento();

                    movimiento.setFecha(formatearFecha(fechaYHoraActual));
                    movimiento.setHora(formatearHora(fechaYHoraActual));
                    movimiento.setDescripcion("Retiro");
                    movimiento.setImporte(montoRetiro);
                    movimiento.setTipoCambio(tipoCambio);

                    // Se agrega el movimiento a la lista de movimientos del usuario
                    cuentas[indiceCuenta].agregarMovimiento(movimiento);

                    // Aumenta el contado de numero de retiros
                    numeroRetiros++;

                    // Cobro de comision a partir de la tercera operacion;
                    double saldoDescontado;
                    if (numeroRetiros > 2) {
                        saldoDescontado = cuentas[indiceCuenta].getSaldo() - 0.45;
                        cuentas[indiceCuenta].setSaldo(saldoDescontado);

                        // Se agrega el movimiento de la comision
                        Movimiento comision = new Movimiento();
                        comision.setFecha(formatearFecha(fechaYHoraActual));
                        comision.setHora(formatearHora(fechaYHoraActual));
                        comision.setDescripcion("Comision Banco");
                        comision.setImporte(0.45);
                        comision.setTipoCambio(0);
                        cuentas[indiceCuenta].agregarMovimiento(comision);
                    }

                    lblMensajeRetiro.setText(
                            "<html>" +
                                    "SALDO DISPONIBLE ANTERIOR: S/. "
                                    + convertirADosDecimales(saldoAnterior) + "<br>" +
                                    "IMPORTE RETIRADO EN SOLES: S/. " + convertirADosDecimales(movimiento.getImporte())
                                    + "<br>" +
                                    "SALDO DISPONIBLE ACTUAL: S/. "
                                    + convertirADosDecimales(cuentas[indiceCuenta].getSaldo()) + "</html>");

                } else {
                    lblMensajeRetiro.setForeground(Color.RED);
                    lblMensajeRetiro.setText("Saldo insuficiente. No se puede realizar el retiro.");
                }
            } else {
                lblMensajeRetiro.setForeground(Color.RED);
                lblMensajeRetiro.setText("El limite maximo de retiro por dia es S/ 1500.");
            }
        } else {
            lblMensajeRetiro.setForeground(Color.RED);
            lblMensajeRetiro.setText("La cantidad a retirar debe ser un multiplo de 50.");
        }

        // Para dolares
        if (isDolares) {
            // Validar limite maximo de retiro por dia (S/. 1500)
            if (montoRetiro <= montoLimiteDiario) {
                // Verificar si hay saldo suficiente en la cuenta

                if (montoRetiro <= cuentas[indiceCuenta].getSaldo()) {

                    // Se ocultan las caja de texto y el boton
                    txtCantidadRetiro.setVisible(false);
                    btnRetirarDinero.setVisible(false);

                    // Realizar el retiro y actualiza el saldo
                    double saldoAnterior = cuentas[indiceCuenta].getSaldo();
                    double saldoNuevo = saldoAnterior - montoRetiro;
                    cuentas[indiceCuenta].setSaldo(saldoNuevo);

                    // Se suma el monto total retirado que se usara para el reporte
                    saldoTotalRetirado = saldoTotalRetirado + montoRetiro;

                    // Se reduce el monto de limite diario
                    montoLimiteDiario = montoLimiteDiario - montoRetiro;

                    // Ingreso del movimiento de la cuenta
                    Date fechaYHoraActual = new Date();
                    Movimiento movimiento = new Movimiento();

                    movimiento.setFecha(formatearFecha(fechaYHoraActual));
                    movimiento.setHora(formatearHora(fechaYHoraActual));
                    movimiento.setDescripcion("Retiro");
                    movimiento.setImporte(montoRetiro);
                    movimiento.setTipoCambio(tipoCambio);

                    // Se agrega el movimiento a la lista de movimientos del usuario
                    cuentas[indiceCuenta].agregarMovimiento(movimiento);

                    // Aumenta el contado de numero de retiros
                    numeroRetiros++;

                    // Cobro de comision a partir de la tercera operacion;
                    double saldoDescontado;
                    if (numeroRetiros > 2) {
                        saldoDescontado = cuentas[indiceCuenta].getSaldo() - 0.45;
                        cuentas[indiceCuenta].setSaldo(saldoDescontado);

                        // Se agrega el movimiento de la comision
                        Movimiento comision = new Movimiento();
                        comision.setFecha(formatearFecha(fechaYHoraActual));
                        comision.setHora(formatearHora(fechaYHoraActual));
                        comision.setDescripcion("Comision Banco");
                        comision.setImporte(0.45);
                        comision.setTipoCambio(0);
                        cuentas[indiceCuenta].agregarMovimiento(comision);
                    }

                    lblMensajeRetiro.setText(
                            "<html>" +
                                    "SALDO DISPONIBLE ANTERIOR: S/. "
                                    + convertirADosDecimales(saldoAnterior) + "<br>" +
                                    "IMPORTE RETIRADO EN DOLARES: $ " + convertirADosDecimales(montoEnDolares) + "<br>"
                                    +
                                    "TIPO DE CAMBIO S/. : " + convertirADosDecimales(movimiento.getTipoCambio())
                                    + "<br>"
                                    +
                                    "SALDO DISPONIBLE ACTUAL: S/. "
                                    + convertirADosDecimales(cuentas[indiceCuenta].getSaldo()) + "<br>" +
                                    "IMPORTE EQUIVALENTE EN SOLES: S/. "
                                    + convertirADosDecimales(movimiento.getImporte())
                                    + "</html>");

                } else {
                    lblMensajeRetiro.setForeground(Color.RED);
                    lblMensajeRetiro.setText("Saldo insuficiente. No se puede realizar el retiro.");
                }
            } else {
                lblMensajeRetiro.setForeground(Color.RED);
                lblMensajeRetiro.setText("El limite maximo de retiro por dia es S/ 1500.");
            }
        }
    }
}