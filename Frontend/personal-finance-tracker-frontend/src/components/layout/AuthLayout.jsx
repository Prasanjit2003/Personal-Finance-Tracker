const AuthLayout = ({ title, subtitle, children }) => {
  return (
    <div className="container-fluid min-vh-100 d-flex align-items-center justify-content-center">
      <div className="row w-100 justify-content-center">
        <div className="col-11 col-sm-9 col-md-6 col-lg-4">
          <div className="card shadow-sm p-4">
            <h3 className="text-center fw-bold mb-1">{title}</h3>
            {subtitle && (
              <p className="text-center text-muted mb-4">{subtitle}</p>
            )}
            {children}
          </div>
        </div>
      </div>
    </div>
  );
};

export default AuthLayout;
