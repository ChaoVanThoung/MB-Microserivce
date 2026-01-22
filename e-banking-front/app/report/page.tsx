import LogoutBtnComponent from "@/components/LogoutbtnComponent";
import AccountComponent from "@/features/accounts/components/AccountComponent";

export default function page() {
  return (
    <>
      <section className="py-4">
        <div className="container m-auto">
          <div>
            <h1 className="text-5xl font-bold text-red-500">Report Data All Univers</h1>
           <AccountComponent/>
          </div>
          <LogoutBtnComponent/>
        </div>
      </section>
    </>
  );
}
