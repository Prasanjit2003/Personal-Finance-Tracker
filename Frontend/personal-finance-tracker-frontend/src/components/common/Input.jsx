const Input = ({ label, error, ...props }) => {
  return (
    <div className="mb-3">
      {label && <label className="form-label">{label}</label>}
      <input className="form-control" {...props} />
      {error && <small className="text-danger">{error}</small>}
    </div>
  );
};

export default Input;
