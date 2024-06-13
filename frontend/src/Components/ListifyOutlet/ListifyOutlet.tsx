import { Outlet } from "react-router-dom";
import { NavBar } from "../NavBar";

export function ListifyOutlet() {
  return (
    <>
      <NavBar />
      <Outlet />
    </>
  )
}
