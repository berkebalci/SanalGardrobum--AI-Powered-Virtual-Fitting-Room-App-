import type { RouteObject } from "react-router-dom";
import NotFound from "../pages/NotFound";
import Home from "../pages/home/page";
import Upload from "../pages/upload/page";
import BodyAnalysis from "../pages/body-analysis/page";
import TryOn from "../pages/try-on/page";
import SimulationResult from "../pages/simulation-result/page";
import Combinations from "../pages/combinations/page";
import Settings from "../pages/settings/page";
import Wardrobe from "../pages/wardrobe/page";
import CombinationDetail from "../pages/combination-detail/page";

const routes: RouteObject[] = [
  {
    path: "/",
    element: <Home />,
  },
  {
    path: "/upload",
    element: <Upload />,
  },
  {
    path: "/body-analysis",
    element: <BodyAnalysis />,
  },
  {
    path: "/try-on",
    element: <TryOn />,
  },
  {
    path: "/simulation-result",
    element: <SimulationResult />,
  },
  {
    path: "/combinations",
    element: <Combinations />,
  },
  {
    path: "/wardrobe",
    element: <Wardrobe />,
  },
  {
    path: "/settings",
    element: <Settings />,
  },
  {
    path: "/profile",
    element: <Settings />,
  },
  {
    path: "/combination-detail/:id",
    element: <CombinationDetail />,
  },
  {
    path: "*",
    element: <NotFound />,
  },
];

export default routes;
