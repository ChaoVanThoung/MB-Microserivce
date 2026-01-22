import { redirect } from 'next/navigation';

export default async function Dashboard() {
  // const { userId } = await auth();

  const oauth = false;
  if (!oauth) {
    return redirect('/oauth2/authorization/itpontbff-admin');
  } else {
  redirect('/dashboard/overview');
  }
}
