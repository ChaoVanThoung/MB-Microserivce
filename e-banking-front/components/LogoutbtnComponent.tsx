

'use client';

import { useRouter } from "next/navigation";

export default function LogoutBtnComponent({
  onLogout,
}: {
  onLogout: () => void;
}) {

  const router = useRouter();

  const handleLogout = async () => {
    try {
      const response = await fetch("http://localhost:9990/logout", {
        method: "POST",
        credentials: "include", // include cookies/session
      });

      if (response.ok) {
        // Redirect to frontend landing page after logout
        router.push("/");
      } else {
        console.error("Logout failed");
      }
    } catch (err) {
      console.error("Error logging out:", err);
    }
  };

  return (
    <button
      onClick={handleLogout}
      className="px-4 py-2 bg-red-500 text-white rounded hover:bg-red-600"
    >
      Logout
    </button>
  );
}
