/**
 * This code is part of the skeleton project provided for students of the course "Software
 * Architecture" offered by Innsbruck University.
 */
import {API_BASE_URL} from "../config/config";
import {useNavigate} from "react-router-dom";
import {useUser} from "../Contexts/AuthenticatedUserContext";
import {useEffect} from "react";

/**
 * Logout component
 */

const Logout = () => {

    const navigate = useNavigate();
    const {logout} = useUser();

    useEffect(() => {
        const handleLogout = async () => {
            try {
                // TODO: Switch to using axios and add this to a seperate file
                const response = await fetch(`${API_BASE_URL}/api/logout`, {
                    method: 'POST',
                    credentials: 'include', // Sendet Cookies mit der Anfrage
                });

                if (response.ok) {
                    console.log(response);
                } else {
                    console.error('Logout fehlgeschlagen.');
                }

            } catch (err) {
                console.error('Fehler beim Logout:', err);
            } finally {
                // clear user data via user context regardless of success or failure
                logout();
                localStorage.setItem('isAuthenticated', 'false');
                // redirect to login page
                navigate('/login');
            }
        };
        handleLogout();
    }, [logout, navigate]);

    return (
        <div>
        </div>
    )

}

export default Logout
