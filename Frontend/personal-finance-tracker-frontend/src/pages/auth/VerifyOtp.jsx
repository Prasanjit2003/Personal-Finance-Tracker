import { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import AuthLayout from "../../components/layout/AuthLayout";
import { verifyOtp } from "../../services/authService";

const VerifyOtp = () => {
  const { state } = useLocation();
  const navigate = useNavigate();
  const email = state?.email;

  const [otp, setOtp] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!email) {
      alert("Email not found. Please register again.");
      return;
    }

    setLoading(true);

    try {
      await verifyOtp({ email, otp });
      alert("OTP verified successfully");
      navigate("/login");
    } catch (err) {
      alert("Invalid OTP");
    } finally {
      setLoading(false);
    }
  };

  return (
    <AuthLayout
      title="Verify OTP"
      subtitle={`OTP sent to ${email}`}
    >
      <form onSubmit={handleSubmit}>
        <input
          className="form-control mb-3 text-center"
          placeholder="Enter OTP"
          value={otp}
          onChange={(e) => setOtp(e.target.value)}
          required
        />

        <button
          className="btn btn-primary w-100"
          disabled={loading}
        >
          {loading ? "Verifying..." : "Verify"}
        </button>
      </form>
    </AuthLayout>
  );
};

export default VerifyOtp;
