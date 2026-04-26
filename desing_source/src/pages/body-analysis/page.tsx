
import { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';

export default function BodyAnalysis() {
  const navigate = useNavigate();
  const location = useLocation();
  const { image, measurements } = location.state || {};
  const [progress, setProgress] = useState(0);
  const [currentStep, setCurrentStep] = useState(0);

  const steps = [
    { label: 'Fotoğraf İşleniyor', icon: 'ri-image-line' },
    { label: 'Vücut Segmentasyonu', icon: 'ri-body-scan-line' },
    { label: 'Poz Analizi', icon: 'ri-user-search-line' },
    { label: 'Oran Hesaplama', icon: 'ri-ruler-line' },
    { label: 'Tamamlanıyor', icon: 'ri-check-line' }
  ];

  useEffect(() => {
    if (!image) {
      navigate('/upload');
      return;
    }

    const interval = setInterval(() => {
      setProgress((prev) => {
        if (prev >= 100) {
          clearInterval(interval);
          setTimeout(() => {
            navigate('/try-on', { state: { image, measurements } });
          }, 500);
          return 100;
        }
        return prev + 2;
      });
    }, 100);

    return () => clearInterval(interval);
  }, [image, navigate, measurements]);

  useEffect(() => {
    const stepIndex = Math.floor(progress / 20);
    setCurrentStep(Math.min(stepIndex, steps.length - 1));
  }, [progress, steps.length]);

  const analysisResults = [
    { label: 'Vücut Tipi', value: 'Kum Saati', icon: 'ri-user-line' },
    { label: 'Omuz Genişliği', value: '42 cm', icon: 'ri-arrow-left-right-line' },
    { label: 'Bel Oranı', value: '0.68', icon: 'ri-percent-line' },
    { label: 'Boy/Kilo Oranı', value: 'İdeal', icon: 'ri-heart-pulse-line' }
  ];

  return (
    <div className="min-h-screen bg-gradient-to-br from-purple-50 via-pink-50 to-blue-50">
      {/* Header */}
      <div className="fixed top-0 left-0 right-0 bg-white/80 backdrop-blur-md z-50 px-5 py-4 shadow-sm">
        <div className="flex items-center gap-3">
          <button 
            onClick={() => navigate('/upload')}
            className="w-9 h-9 flex items-center justify-center rounded-full hover:bg-gray-100 transition-colors"
          >
            <i className="ri-arrow-left-line text-xl text-gray-700"></i>
          </button>
          <h1 className="text-lg font-bold text-gray-800">Vücut Analizi</h1>
        </div>
      </div>

      <div className="pt-20 px-5 pb-8">
        {/* Image Preview */}
        <div className="relative mb-6">
          <div className="bg-white rounded-3xl overflow-hidden shadow-lg">
            <img 
              src={image} 
              alt="Analysis" 
              className="w-full h-80 object-cover object-top"
            />
            <div className="absolute inset-0 bg-gradient-to-t from-black/50 to-transparent"></div>
            
            {/* Scanning Effect */}
            <div 
              className="absolute left-0 right-0 h-1 bg-gradient-to-r from-transparent via-purple-500 to-transparent"
              style={{ 
                top: `${progress}%`,
                transition: 'top 0.1s linear',
                boxShadow: '0 0 20px rgba(168, 85, 247, 0.8)'
              }}
            ></div>
          </div>
        </div>

        {/* Progress */}
        <div className="bg-white rounded-3xl p-5 shadow-sm mb-6">
          <div className="flex items-center justify-between mb-3">
            <h3 className="font-bold text-gray-800">Analiz İlerlemesi</h3>
            <span className="text-2xl font-bold bg-gradient-to-r from-purple-600 to-pink-600 bg-clip-text text-transparent">
              {progress}%
            </span>
          </div>
          
          <div className="relative h-3 bg-gray-100 rounded-full overflow-hidden mb-4">
            <div 
              className="absolute left-0 top-0 h-full bg-gradient-to-r from-purple-500 to-pink-500 rounded-full transition-all duration-300"
              style={{ width: `${progress}%` }}
            ></div>
          </div>

          <div className="space-y-3">
            {steps.map((step, index) => (
              <div 
                key={index}
                className={`flex items-center gap-3 transition-all ${
                  index <= currentStep ? 'opacity-100' : 'opacity-30'
                }`}
              >
                <div className={`w-8 h-8 rounded-full flex items-center justify-center flex-shrink-0 ${
                  index < currentStep 
                    ? 'bg-green-500' 
                    : index === currentStep 
                    ? 'bg-gradient-to-br from-purple-500 to-pink-500' 
                    : 'bg-gray-200'
                }`}>
                  {index < currentStep ? (
                    <i className="ri-check-line text-white text-lg"></i>
                  ) : (
                    <i className={`${step.icon} text-white text-lg ${index === currentStep ? 'animate-pulse' : ''}`}></i>
                  )}
                </div>
                <span className={`text-sm font-medium ${
                  index <= currentStep ? 'text-gray-800' : 'text-gray-400'
                }`}>
                  {step.label}
                </span>
              </div>
            ))}
          </div>
        </div>

        {/* Analysis Results Preview */}
        {progress > 50 && (
          <div className="bg-white rounded-3xl p-5 shadow-sm animate-fade-in">
            <h3 className="font-bold text-gray-800 mb-4 flex items-center gap-2">
              <i className="ri-bar-chart-box-line text-purple-600"></i>
              Analiz Sonuçları
            </h3>
            <div className="grid grid-cols-2 gap-3">
              {analysisResults.map((result, index) => (
                <div 
                  key={index}
                  className="bg-gradient-to-br from-purple-50 to-pink-50 rounded-2xl p-4"
                >
                  <div className="w-10 h-10 rounded-xl bg-white flex items-center justify-center mb-2">
                    <i className={`${result.icon} text-purple-600 text-lg`}></i>
                  </div>
                  <p className="text-xs text-gray-600 mb-1">{result.label}</p>
                  <p className="text-sm font-bold text-gray-800">{result.value}</p>
                </div>
              ))}
            </div>
          </div>
        )}

        {/* AI Info */}
        <div className="mt-6 bg-gradient-to-r from-purple-500 to-pink-500 rounded-2xl p-4 text-white">
          <div className="flex items-start gap-3">
            <div className="w-10 h-10 rounded-full bg-white/20 flex items-center justify-center flex-shrink-0">
              <i className="ri-robot-line text-xl"></i>
            </div>
            <div>
              <h4 className="font-semibold mb-1">Yapay Zeka Analizi</h4>
              <p className="text-xs text-white/90 leading-relaxed">
                Gelişmiş yapay zeka algoritmaları ile vücut segmentasyonu, poz analizi ve oran hesaplaması yapılıyor.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
